package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.model.error.ErrorS;
import com.jeramtough.randl2.model.error.ErrorU;
import com.jeramtough.randl2.model.params.registereduser.*;
import com.jeramtough.randl2.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.component.registereduser.builder.*;
import com.jeramtough.randl2.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.model.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.model.dto.RegisteredUserDto;
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
    private RoleMapper roleMapper;

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc,
                                     MapperFacade mapperFacade,
                                     RedisVerificationCodeHolder verificationCodeHolder,
                                     PasswordEncoder passwordEncoder,
                                     SurfaceImageMapper surfaceImageMapper,
                                     RoleMapper roleMapper) {
        super(wc, mapperFacade);
        this.verificationCodeHolder = verificationCodeHolder;
        this.passwordEncoder = passwordEncoder;
        this.surfaceImageMapper = surfaceImageMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        registeredUserDto.setRole(roleMapper.selectById(registeredUser.getRoleId()));
        return registeredUserDto;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailForNew(
            VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());
        String transactionId = builder.setAccount(params.getPhoneOrEmail(),
                ErrorU.CODE_2.C, ErrorU.CODE_201.C, ErrorU.CODE_202.C);

        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以注册");
        return map;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailByForget(
            VerifyPhoneOrEmailByForgetParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserRebuilder rebuilder = getRegisteredUserRebuilder();

        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(params.getWay());
        switch (Objects.requireNonNull(registerUserWay)) {
            case PHONE_USER_WAY:
                if (!ValidationUtil.isPhone(params.getPhoneOrEmail())) {
                    throw new ApiResponseException(668, "手机号码", "格式不正确");
                }
                break;
            case EMAIL_USER_WAY:
                if (!ValidationUtil.isEmail(params.getPhoneOrEmail())) {
                    throw new ApiResponseException(668, "邮箱地址", "格式不正确");
                }
                break;
            default:
        }

        RegisteredUser registeredUser =
                getBaseMapper().selectByPhoneNumberOrEmailAddress(params.getPhoneOrEmail());
        if (registeredUser == null) {
            throw new ApiResponseException(ErrorU.CODE_203.C);
        }

        String transactionId = rebuilder.rebuildRegisteredUser(registeredUser);
        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以重置");
        return map;
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        builder.setPassword(params.getTransactionId(), params.getPassword(),
                params.getRepeatedPassword(), ErrorU.CODE_205.C, ErrorS.CODE_101.C);
        return "密码校验通过";
    }

    @Override
    public Object verifyPasswordByForget(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        RegisteredUserRebuilder rebuilder = getRegisteredUserRebuilder();
        rebuilder.setPassword(params.getTransactionId(), params.getPassword(),
                params.getRepeatedPassword(), ErrorU.CODE_205.C, ErrorS.CODE_101.C,
                ErrorU.CODE_206.C);
        return "密码校验通过";
    }

    @Override
    public synchronized RegisteredUserDto register(DoRegisterOrResetParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        RegisteredUser registeredUser = builder.build(params.getTransactionId(),
                ErrorS.CODE_101.C, ErrorU.CODE_207.C);

        String phoneOrEmailOrOther = null;
        switch (builder.getRegisterUserWay()) {
            case PHONE_USER_WAY:
                phoneOrEmailOrOther = registeredUser.getPhoneNumber();
                break;
            case EMAIL_USER_WAY:
                phoneOrEmailOrOther = registeredUser.getEmailAddress();
                break;
            default:
                break;
        }
        if (!verificationCodeHolder.getVerificationResult(phoneOrEmailOrOther)) {
            throw new ApiResponseException(ErrorU.CODE_208.C);
        }

        getBaseMapper().insert(registeredUser);
        builder.clear(params.getTransactionId());
        return getBaseDto(registeredUser);
    }

    @Override
    public synchronized RegisteredUserDto reset(DoRegisterOrResetParams params) {
        RegisteredUserRebuilder rebuilder = getRegisteredUserRebuilder();
        RegisteredUser registeredUser = rebuilder.reset(params.getTransactionId(),
                ErrorU.CODE_209.C,
                ErrorU.CODE_210.C);

        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(params.getWay());
        String phoneOrEmailOrOther = null;
        switch (Objects.requireNonNull(registerUserWay)) {
            case PHONE_USER_WAY:
                phoneOrEmailOrOther = registeredUser.getPhoneNumber();
                break;
            case EMAIL_USER_WAY:
                phoneOrEmailOrOther = registeredUser.getEmailAddress();
                break;
            default:
                break;
        }
        if (!verificationCodeHolder.getVerificationResult(phoneOrEmailOrOther)) {
            throw new ApiResponseException(ErrorU.CODE_208.C);
        }

        getBaseMapper().updateById(registeredUser);
        rebuilder.clear(params.getTransactionId());
        return getBaseDto(registeredUser);
    }

    @Override
    public String removeRegisteredUser(Long uid) {
        boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(ErrorS.CODE_1.C, "普通注册用户");
        }
        return "移除普通注册用户成功";
    }

    @Override
    public String updateRegisteredUser(UpdateRegisteredUserParams params) {
        BeanValidator.verifyDto(params);

        RegisteredUser currentRegisteredUser = getById(params.getUid());

        if (currentRegisteredUser == null) {
            throw new ApiResponseException(ErrorS.CODE_1.C,"普通注册用户");
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

        if (!currentRegisteredUser.getRoleId().equals(params.getRoleId())) {
            if (roleMapper.selectById(params.getRoleId()) == null) {
                //该角色不存在
                throw new ApiResponseException(1039);
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
            throw new ApiResponseException(ErrorS.CODE_2.C,"普通注册用户");
        }
        return getDtoList(registeredUserList);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    //****************************

    private RegisteredUserBuilder getRegisteredUserBuilder(int way) {
        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(way);
        if (registerUserWay == null) {
            throw new ApiResponseException(ErrorU.CODE_5.C, "way参数只能填1(以手机)或2(以邮箱)");
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

    private RegisteredUserRebuilder getRegisteredUserRebuilder() {
        RegisteredUserRebuilder registeredUserRebuilder =
                getWC().getBean(MyRegisteredUserRebuiler.class);
        return registeredUserRebuilder;
    }
}


