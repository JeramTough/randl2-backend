package com.jeramtough.randl2.service.user.impl;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.userdetail.RegisterUserWay;
import com.jeramtough.randl2.common.component.userdetail.builder.UserBuilderFactory;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.TransactionTimeoutExcaption;
import com.jeramtough.randl2.common.component.userdetail.builder.news.NewUserBuilder;
import com.jeramtough.randl2.common.component.userdetail.builder.reset.ResetUserBuilder;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;
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
public class UserRegisterServiceImpl extends BaseServiceImpl implements UserRegisterService {

    private final RandlUserMapper randlUserMapper;

    @Autowired
    public UserRegisterServiceImpl(WebApplicationContext wc,
                                   RandlUserMapper randlUserMapper) {
        super(wc);
        this.randlUserMapper = randlUserMapper;
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
    public Object verifyPasswordByForget(VerifyPasswordParams params) {
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
    public Object register(DoRegisterOrResetParams params) {

        return null;
    }

    @Override
    public Object reset(DoRegisterOrResetParams params) {
        return null;
    }
}
