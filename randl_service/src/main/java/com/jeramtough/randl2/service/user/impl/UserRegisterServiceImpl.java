package com.jeramtough.randl2.service.user.impl;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.detail.userdetail.RegisterUserWay;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.UserBuilderFactory;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.NoChangedException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.NotSetPasswordException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.news.NewUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.reset.ResetUserBuilder;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.common.model.params.verificationcode.GetVerifiedResultParams;
import com.jeramtough.randl2.service.resource.VerificationCodeService;
import com.jeramtough.randl2.service.user.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * Created on 2021/1/17 22:53
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class UserRegisterServiceImpl extends BaseServiceImpl implements UserRegisterService, WithLogger {

    private final RandlUserMapper randlUserMapper;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public UserRegisterServiceImpl(WebApplicationContext wc,
                                   RandlUserMapper randlUserMapper,
                                   VerificationCodeService verificationCodeService) {
        super(wc);
        this.randlUserMapper = randlUserMapper;
        this.verificationCodeService = verificationCodeService;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyParams(params);

        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(params.getWay());
        switch (Objects.requireNonNull(registerUserWay)) {
            case EMAIL_USER_WAY:
                if (randlUserMapper.selectByEmailAddress(params.getPhoneOrEmail()) != null) {
                    throw new ApiResponseBeanException(ErrorU.CODE_12.C, "email", "邮箱注册用户");
                }
                break;
            case PHONE_USER_WAY:
                if (randlUserMapper.selectByPhoneNumber(params.getPhoneOrEmail()) != null) {
                    throw new ApiResponseBeanException(ErrorU.CODE_12.C, "phone", "手机注册用户");
                }
                break;
            default:
        }

        NewUserBuilder newBuilder = UserBuilderFactory.getNewUserBuilder(getWC(), params.getWay());

        String transactionId = newBuilder.start();
        try {
            newBuilder.setAccount(transactionId, params.getPhoneOrEmail());
        }
        catch (AccountFormatException e) {
            throw new ApiResponseBeanException(ErrorU.CODE_8.C, e.getField(), e.getMessage());
        }
        catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
            throw new ApiResponseException(ErrorU.CODE_201.C);
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以注册");
        return map;
    }

    @Override
    public Map<String, Object> verifyPhoneOrEmailByForget(VerifyPhoneOrEmailByForgetParams params) {
        BeanValidator.verifyParams(params);

        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(params.getWay());
        RandlUser randlUser = null;
        switch (Objects.requireNonNull(registerUserWay)) {
            case EMAIL_USER_WAY:
                randlUser = randlUserMapper.selectByEmailAddress(params.getPhoneOrEmail());
                if (randlUser == null) {
                    throw new ApiResponseBeanException(ErrorU.CODE_10.C, "email", "邮箱注册用户");
                }
                break;
            case PHONE_USER_WAY:
                randlUser = randlUserMapper.selectByPhoneNumber(params.getPhoneOrEmail());
                if (randlUser == null) {
                    throw new ApiResponseBeanException(ErrorU.CODE_10.C, "phone", "手机注册用户");
                }
                break;
            default:
        }

        //start to rebuild the user.
        Objects.requireNonNull(randlUser);
        ResetUserBuilder resetUserBuilder = UserBuilderFactory.getResetUserBuilder(getWC(), params.getWay());
        String transactionId = resetUserBuilder.start();
        resetUserBuilder.rebuildRegisteredUser(transactionId, randlUser);


        Map<String, Object> map = new HashMap<>(2);
        map.put("transactionId", transactionId);
        map.put("message", "该账号可以重置");
        return map;
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyParams(params);

        if (!params.getPassword().equals(params.getRepeatedPassword())) {
            throw new ApiResponseException(ErrorU.CODE_101.C);
        }

        NewUserBuilder newBuilder = UserBuilderFactory.getNewUserBuilder(getWC(), params.getWay());
        try {
            newBuilder.setPassword(params.getTransactionId(), params.getPassword());
        }
        catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
            throw new ApiResponseException(ErrorU.CODE_201.C);
        }
        return "密码设置成功！";
    }

    @Override
    public String verifyPasswordByForget(VerifyPasswordParams params) {
        BeanValidator.verifyParams(params);

        if (!params.getPassword().equals(params.getRepeatedPassword())) {
            throw new ApiResponseException(ErrorU.CODE_101.C);
        }

        ResetUserBuilder resetUserBuilder = UserBuilderFactory.getResetUserBuilder(getWC(), params.getWay());
        try {
            resetUserBuilder.setPassword(params.getTransactionId(), params.getPassword());
        }
        catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
            throw new ApiResponseException(ErrorU.CODE_201.C);
        }
        return "密码设置成功！";
    }

    @Override
    public RandlUserDto register(DoRegisterOrResetParams params) {
        BeanValidator.verifyParams(params);

        NewUserBuilder newUserBuilder = UserBuilderFactory.getNewUserBuilder(getWC(), params.getWay());

        GetVerifiedResultParams getVerifiedResultParams = new GetVerifiedResultParams();
        try {
            getVerifiedResultParams.setPhoneOrEmail(
                    newUserBuilder.getRegisterUserWayForPhoneOrEmail(params.getTransactionId()));
        }
        catch (TransactionTimeoutExcaption e) {
            throw new ApiResponseException(ErrorU.CODE_201.C);
        }
        boolean isPassed = verificationCodeService.getVerifiedResult(getVerifiedResultParams);

        if (isPassed) {
            RandlUser randlUser;
            try {
                randlUser = newUserBuilder.build(params.getTransactionId());
            }
            catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
                throw new ApiResponseException(ErrorU.CODE_201.C);
            }
            catch (NotSetPasswordException e) {
                throw new ApiResponseException(ErrorU.CODE_202.C);
            }

            Objects.requireNonNull(randlUser);
            getLogger().info(params.getTransactionId() + "用户注册校验通过");

            int affectCount = randlUserMapper.insert(randlUser);
            if (affectCount > 0) {
                getLogger().info("插入新用户数据成功！");
                RandlUserDto randlUserDto = getMapperFacade().map(randlUser, RandlUserDto.class);
                return randlUserDto;
            }
            else {
                throw new ApiResponseException(ErrorS.CODE_1.C);
            }
        }

        return null;
    }

    @Override
    public RandlUserDto reset(DoRegisterOrResetParams params) {
        BeanValidator.verifyParams(params);

        ResetUserBuilder resetUserBuilder = UserBuilderFactory.getResetUserBuilder(getWC(), params.getWay());

        GetVerifiedResultParams getVerifiedResultParams = new GetVerifiedResultParams();
        try {
            getVerifiedResultParams.setPhoneOrEmail(
                    resetUserBuilder.getResetUserWayForPhoneOrEmail(params.getTransactionId()));
        }
        catch (TransactionTimeoutExcaption e) {
            throw new ApiResponseException(ErrorU.CODE_201.C);
        }
        boolean isPassed = verificationCodeService.getVerifiedResult(getVerifiedResultParams);

        if (isPassed) {
            try {
                getLogger().info(params.getTransactionId() + "用户重置密码校验通过");
                RandlUser randlUser = resetUserBuilder.reset(params.getTransactionId());

                int affectCount = randlUserMapper.updateById(randlUser);
                if (affectCount > 0) {
                    getLogger().info("更新用户数据成功！");
                    RandlUserDto randlUserDto = getMapperFacade().map(randlUser, RandlUserDto.class);
                    return randlUserDto;
                }
                else {
                    throw new ApiResponseException(ErrorS.CODE_1.C);
                }

            }
            catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
                throw new ApiResponseException(ErrorU.CODE_201.C);
            }
            catch (NoChangedException e) {
                throw new ApiResponseException(ErrorU.CODE_203.C);
            }
        }

        return null;
    }
}
