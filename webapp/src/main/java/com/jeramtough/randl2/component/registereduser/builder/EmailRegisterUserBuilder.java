package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
            RegisteredUserMapper registeredUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        super(passwordEncoder, registeredUserMapper, redisTemplate);
    }


    @Override
    public String setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {
        boolean isRightFormat = ValidationUtil.isEmail(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[1]);
        }
        if (getRegisteredUserMapper().selectByEmailAddress(phoneOrEmailOrOther) != null) {
            throw new ApiResponseException(errorCodes[3]);
        }

        String transactionId = createTransactionId();
        setEmailAddress(transactionId, phoneOrEmailOrOther);
        return transactionId;
    }


    @Override
    public RegisteredUser build(String transactionId, int... errorCodes) throws
            ApiResponseException {
        String emailAddress = getEmailAddress(transactionId, errorCodes[0]);
        String password = getPassword(transactionId, errorCodes[0]);
        if (emailAddress == null || password == null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress(emailAddress);
        registeredUser.setPassword(getPasswordEncoder().encode(password));
        registeredUser.setAccount("E_" + emailAddress.substring(0,
                Math.min(emailAddress.length(), 16)));
        registeredUser.setRegistrationTime(LocalDateTime.now());
        registeredUser.setAccountStatus(1);
        registeredUser.setSurfaceImageId(2L);
        return registeredUser;
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
