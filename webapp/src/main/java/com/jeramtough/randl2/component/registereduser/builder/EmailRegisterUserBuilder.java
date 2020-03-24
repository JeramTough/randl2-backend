package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
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
        boolean isRightFormat = ValidationUtil.isPhone(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[0]);
        }
        if (getRegisteredUserMapper().selectByPhoneNumber(phoneOrEmailOrOther) != null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        String transactionId = createTransactionId();
        setPhoneNumber(transactionId, errorCodes[2], phoneOrEmailOrOther);
        return transactionId;
    }


    @Override
    public RegisteredUser build(String transactionId, int... errorCodes) throws
            ApiResponseException {
        String phoneNumber = getPhoneNumber(transactionId, errorCodes[0]);
        String password = getPassword(transactionId, errorCodes[0]);
        if (phoneNumber == null || password == null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setPhoneNumber(phoneNumber);
        registeredUser.setPassword(password);
        registeredUser.setAccount("phone_" + IdUtil.getUUID().substring(0, 8));
        registeredUser.setRegistrationTime(LocalDateTime.now());
        registeredUser.setAccountStatus(1);
        registeredUser.setSurfaceImageId(2L);
        return registeredUser;
    }

    //************

    private String getPhoneNumber(String transactionId, int errorCode) {
        return (String) getHashOperations(transactionId, errorCode).get("phoneNumber");
    }


    private void setPhoneNumber(String transactionId, int errorCode, String phoneNumber) {
        getHashOperations(transactionId, errorCode).put("phoneNumber", phoneNumber);
    }


}
