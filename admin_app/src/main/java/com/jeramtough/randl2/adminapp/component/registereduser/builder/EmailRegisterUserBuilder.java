package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.adminapp.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/24 16:30
 * by @author JeramTough
 * </pre>
 */
@Component
public class EmailRegisterUserBuilder extends CommonUserBuilder
        implements RegisteredUserBuilder {

    @Autowired
    public EmailRegisterUserBuilder(
            PasswordEncoder passwordEncoder,
            RandlUserMapper randlUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        super(passwordEncoder, randlUserMapper, redisTemplate);
    }


    @Override
    public String setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {
        boolean isRightFormat = ValidationUtil.isEmail(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[0], "邮箱格式", "例子:11787@qq.com");
        }
        if (getRandlUserMapper().selectByEmailAddress(phoneOrEmailOrOther) != null) {
            throw new ApiResponseException(errorCodes[2]);
        }

        String transactionId = createTransactionId();
        setEmailAddress(transactionId, phoneOrEmailOrOther);
        return transactionId;
    }


    @Override
    public RandlUser build(String transactionId, int... errorCodes) throws
            ApiResponseException {
        String emailAddress = getEmailAddress(transactionId, errorCodes[0]);
        String password = getPassword(transactionId, errorCodes[0]);
        if (emailAddress == null || password == null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        //构建对象公共部门
        RandlUser randlUser = buildTheCommon();

        randlUser.setEmailAddress(emailAddress);
        randlUser.setPassword(getPasswordEncoder().encode(password));
        randlUser.setAccount("E_" + emailAddress.substring(0,
                Math.min(emailAddress.length(), 16)));

        return randlUser;
    }

    @Override
    public RegisterUserWay getRegisterUserWay() {
        return RegisterUserWay.EMAIL_USER_WAY;
    }

    //************

    private String getEmailAddress(String transactionId, int errorCode) {
        return (String) getHashOperations(transactionId, errorCode).get("emailAddress");
    }


    private void setEmailAddress(String transactionId, String phoneNumber) {
        createHashOperations(transactionId).put("emailAddress", phoneNumber);
    }


}
