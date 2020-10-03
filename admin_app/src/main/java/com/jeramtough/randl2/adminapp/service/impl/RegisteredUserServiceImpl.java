package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.registereduser.*;
import com.jeramtough.randl2.adminapp.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.adminapp.component.registereduser.builder.*;
import com.jeramtough.randl2.common.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.adminapp.service.RegisteredUserService;
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
public class RegisteredUserServiceImpl extends BaseServiceImpl<RandlUserMapper,
        RandlUser, RandlUserDto> implements
        RegisteredUserService, WithLogger {

    private RedisVerificationCodeHolder verificationCodeHolder;
    private PasswordEncoder passwordEncoder;
    private SourceSurfaceImageMapper sourceSurfaceImageMapper;
    private RandlRoleMapper randlRoleMapper;

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc,
                                     MapperFacade mapperFacade,
                                     RedisVerificationCodeHolder verificationCodeHolder,
                                     PasswordEncoder passwordEncoder,
                                     SourceSurfaceImageMapper sourceSurfaceImageMapper,
                                     RandlRoleMapper randlRoleMapper) {
        super(wc, mapperFacade);
        this.verificationCodeHolder = verificationCodeHolder;
        this.passwordEncoder = passwordEncoder;
        this.sourceSurfaceImageMapper = sourceSurfaceImageMapper;
        this.randlRoleMapper = randlRoleMapper;
    }

    @Override
    protected RandlUserDto toDto(RandlUser randlUser) {
        RandlUserDto randlUserDto = getMapperFacade().map(randlUser,
                RandlUserDto.class);
        randlUserDto.setRandRole(randlRoleMapper.selectById(randlUser.getRoleId()));
        return randlUserDto;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailForNew(
            VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyParams(params);

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
        BeanValidator.verifyParams(params);

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

        RandlUser randlUser =
                getBaseMapper().selectByPhoneNumberOrEmailAddress(params.getPhoneOrEmail());
        if (randlUser == null) {
            throw new ApiResponseException(ErrorU.CODE_205.C);
        }

        String transactionId = rebuilder.rebuildRegisteredUser(randlUser);
        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以重置");
        return map;
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyParams(params);
        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        builder.setPassword(params.getTransactionId(), params.getPassword(),
                params.getRepeatedPassword(), ErrorU.CODE_101.C, ErrorU.CODE_201.C);
        return "密码校验通过";
    }

    @Override
    public Object verifyPasswordByForget(VerifyPasswordParams params) {
        BeanValidator.verifyParams(params);
        RegisteredUserRebuilder rebuilder = getRegisteredUserRebuilder();
        rebuilder.setPassword(params.getTransactionId(), params.getPassword(),
                params.getRepeatedPassword(), ErrorU.CODE_101.C, ErrorU.CODE_201.C,
                ErrorU.CODE_209.C);
        return "密码校验通过";
    }

    @Override
    public synchronized RandlUserDto register(DoRegisterOrResetParams params) {
        BeanValidator.verifyParams(params);

        RegisteredUserBuilder builder = getRegisteredUserBuilder(params.getWay());

        RandlUser randlUser = builder.build(params.getTransactionId(),
                ErrorU.CODE_201.C, ErrorU.CODE_202.C);

        String phoneOrEmailOrOther = null;
        switch (builder.getRegisterUserWay()) {
            case PHONE_USER_WAY:
                phoneOrEmailOrOther = randlUser.getPhoneNumber();
                break;
            case EMAIL_USER_WAY:
                phoneOrEmailOrOther = randlUser.getEmailAddress();
                break;
            default:
                break;
        }
        if (!verificationCodeHolder.getVerificationResult(phoneOrEmailOrOther)) {
            throw new ApiResponseException(ErrorU.CODE_401.C);
        }

        getBaseMapper().insert(randlUser);
        builder.clear(params.getTransactionId());
        return getBaseDto(randlUser);
    }

    @Override
    public synchronized RandlUserDto reset(DoRegisterOrResetParams params) {
        RegisteredUserRebuilder rebuilder = getRegisteredUserRebuilder();
        RandlUser randlUser = rebuilder.reset(params.getTransactionId(),
                ErrorU.CODE_203.C,
                ErrorU.CODE_204.C);

        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(params.getWay());
        String phoneOrEmailOrOther = null;
        switch (Objects.requireNonNull(registerUserWay)) {
            case PHONE_USER_WAY:
                phoneOrEmailOrOther = randlUser.getPhoneNumber();
                break;
            case EMAIL_USER_WAY:
                phoneOrEmailOrOther = randlUser.getEmailAddress();
                break;
            default:
                break;
        }
        if (!verificationCodeHolder.getVerificationResult(phoneOrEmailOrOther)) {
            throw new ApiResponseException(ErrorU.CODE_401.C);
        }

        getBaseMapper().updateById(randlUser);
        rebuilder.clear(params.getTransactionId());
        return getBaseDto(randlUser);
    }

    @Override
    public String removeRegisteredUser(Long uid) {
        boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "普通注册用户");
        }
        return "移除普通注册用户成功";
    }

    @Override
    public String updateRegisteredUser(UpdateRegisteredUserParams params) {
        BeanValidator.verifyParams(params);

        RandlUser currentRandlUser = getById(params.getUid());

        if (currentRandlUser == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "普通注册用户");
        }


        if (!currentRandlUser.getAccount().equals(params.getAccount())) {
            if (getBaseMapper().selectOne(new QueryWrapper<RandlUser>().eq("account",
                    params.getAccount())) != null) {
                //存在同名用户
                throw new ApiResponseException(ErrorU.CODE_9.C, "帐号名");
            }
        }

        if (currentRandlUser.getPhoneNumber() != null) {
            if (!currentRandlUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RandlUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(ErrorU.CODE_9.C, "手机号码");
                }
            }
        }

        if (currentRandlUser.getEmailAddress() != null) {
            if (!currentRandlUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RandlUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(ErrorU.CODE_9.C, "邮箱地址");
                }
            }
        }

        if (!currentRandlUser.getRoleId().equals(params.getRoleId())) {
            if (randlRoleMapper.selectById(params.getRoleId()) == null) {
                //该角色不存在
                throw new ApiResponseException(1039);
            }
        }


        RandlUser randlUser = getMapperFacade().map(params, RandlUser.class);
        if (params.getPassword() != null) {
            randlUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }

        updateById(randlUser);
        return "更新普通注册用户信息成功！";
    }

    @Override
    public List<RandlUserDto> getRegisteredUsersByKeyword(String keyword) {
        QueryWrapper<RandlUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("account", "%" + keyword + "%").or().like("phone_number",
                "%" + keyword + "%").or().like("email_address", "%" + keyword + "%");
        List<RandlUser> randlUserList = getBaseMapper().selectList(queryWrapper);
        if (randlUserList == null || randlUserList.size() == 0) {
            throw new ApiResponseException(ErrorU.CODE_8.C, "普通注册用户");
        }
        return getDtoList(randlUserList);
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


