package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.registereduser.*;
import com.jeramtough.randl2.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.component.registereduser.builder.EmailRegisterUserBuilder;
import com.jeramtough.randl2.component.registereduser.builder.PhoneRegisterUserBuilder;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilder;
import com.jeramtough.randl2.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.RegisteredUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RegisteredUserServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements
        RegisteredUserService, WithLogger {

    private RedisVerificationCodeHolder verificationCodeHolder;
    private PasswordEncoder passwordEncoder;
    private SurfaceImageMapper surfaceImageMapper;

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc,
                                     MapperFacade mapperFacade,
                                     RedisVerificationCodeHolder verificationCodeHolder,
                                     PasswordEncoder passwordEncoder,
                                     SurfaceImageMapper surfaceImageMapper) {
        super(wc, mapperFacade);
        this.verificationCodeHolder = verificationCodeHolder;
        this.passwordEncoder = passwordEncoder;
        this.surfaceImageMapper = surfaceImageMapper;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        return registeredUserDto;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailForNew(
            VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());
        String transactionId = builder.setAccount(params.getPhoneOrEmail(), 7001, 7003, 7000);

        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以注册");
        return map;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailByForget(
            VerifyPhoneOrEmailByForgetParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        boolean isPhone = ValidationUtil.isPhone(params.getPhoneOrEmail());
        boolean isEmail = ValidationUtil.isEmail(params.getPhoneOrEmail());
        if (!(isPhone || isEmail)) {
            throw new ApiResponseException(668, "账号", "格式不正确");
        }

        RegisteredUser registeredUser =
                getBaseMapper().selectByPhoneNumberOrEmailAddress(params.getPhoneOrEmail());
        if (registeredUser == null) {
            throw new ApiResponseException(7010);
        }

        return new HashMap<>();
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        builder.setPassword(params.getTransactionId(), params.getPassword(),
                params.getRepeatedPassword(), 7020, 7000);
        return "密码校验通过";
    }

    @Override
    public synchronized RegisteredUserDto register(DoRegisterParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        RegisteredUser registeredUser = builder.build(params.getTransactionId(), 7000, 7030);

        if (!verificationCodeHolder.getVerificationResult(
                registeredUser.getPhoneNumber())) {
            throw new ApiResponseException(7031);
        }

        getBaseMapper().insert(registeredUser);
        builder.clear(params.getTransactionId());
        return getBaseDto(registeredUser);
    }

    @Override
    public synchronized RegisteredUserDto resetPassword() {
       /* RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        RegisteredUser registeredUser = builder.resetRegisteredUser(7040);

        //验证码校验是否通过
        if (!verificationCodeHolder.getVerificationResult().isPassed()) {
            throw new ApiResponseException(7041);
        }

        boolean isTheSamePhoneNumber =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getPhoneNumber());
        boolean isTheSameEmailAddress =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getEmailAddress());
        if (!(isTheSamePhoneNumber || isTheSameEmailAddress)) {
            throw new ApiResponseException(7042);
        }

        getBaseMapper().updateById(registeredUser);
        builder.clear();
        return getBaseDto(registeredUser);*/
        return null;
    }

    @Override
    public String removeRegisteredUser(Long uid) {
        boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(7050);
        }
        return "移除普通注册用户成功";
    }

    @Override
    public String updateRegisteredUser(UpdateRegisteredUserParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUser currentRegisteredUser = getById(params.getUid());

        if (currentRegisteredUser == null) {
            throw new ApiResponseException(7060);
        }


        if (!currentRegisteredUser.getAccount().equals(params.getAccount())) {
            if (getBaseMapper().selectOne(new QueryWrapper<RegisteredUser>().eq("account",
                    params.getAccount())) != null) {
                //存在同名用户
                throw new ApiResponseException(7062);
            }
        }

        if (currentRegisteredUser.getPhoneNumber() != null) {
            if (!currentRegisteredUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RegisteredUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(7066);
                }
            }
        }

        if (currentRegisteredUser.getEmailAddress() != null) {
            if (!currentRegisteredUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RegisteredUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(7067);
                }
            }
        }


        RegisteredUser registeredUser = getMapperFacade().map(params, RegisteredUser.class);
        if (params.getPassword() != null) {
            registeredUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }

        updateById(registeredUser);
        return "更新普通注册用户信息成功！";
    }

    @Override
    public List<RegisteredUserDto> getRegisteredUsersByKeyword(String keyword) {
        QueryWrapper<RegisteredUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("account", "%" + keyword + "%").or().like("phone_number",
                "%" + keyword + "%").or().like("email_address", "%" + keyword + "%");
        List<RegisteredUser> registeredUserList = getBaseMapper().selectList(queryWrapper);
        if (registeredUserList == null || registeredUserList.size() == 0) {
            throw new ApiResponseException(7070);
        }
        return getDtoList(registeredUserList);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    //****************************

    public RegisteredUserBuilder getRegisteredUserBuilder(int way) {
        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(way);
        if (registerUserWay == null) {
            throw new ApiResponseException(7005);
        }
        RegisteredUserBuilder builder = null;
        switch (registerUserWay) {
            case PHONE_USER_WAY:
                builder = getWC().getBean(PhoneRegisterUserBuilder.class);
                break;
            case EMAIL_USER_WAY:
                builder = getWC().getBean(EmailRegisterUserBuilder.class);
                break;
            default:
                break;
        }
        Objects.requireNonNull(builder);
        return builder;
    }
}
