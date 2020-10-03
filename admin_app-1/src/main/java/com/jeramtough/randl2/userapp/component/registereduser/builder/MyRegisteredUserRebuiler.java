package com.jeramtough.randl2.userapp.component.registereduser.builder;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/3/24 21:38
 * by @author JeramTough
 * </pre>
 */
@Component
public class MyRegisteredUserRebuiler extends CommonUserBuilder implements
        RegisteredUserRebuilder {

    @Autowired
    protected MyRegisteredUserRebuiler(
            PasswordEncoder passwordEncoder,
            RegisteredUserMapper registeredUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        super(passwordEncoder, registeredUserMapper, redisTemplate);
    }

    @Override
    public String rebuildRegisteredUser(@NotNull RegisteredUser registeredUser) {
        String transactionId = createTransactionId();
        setNewEntity(transactionId, registeredUser);
        return transactionId;
    }

    @Override
    public void setAccount(String transactionId, String account, int... errorCodes) throws
            ApiResponseException {
        RegisteredUser registeredUser = getEntity(transactionId, errorCodes[0]);
        if (registeredUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }
        if (!registeredUser.getAccount().equals(account)) {
            if (getRegisteredUserMapper().selectByAccount(account) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                registeredUser.setAccount(account);
                setEntity(transactionId, errorCodes[0], registeredUser);
            }
        }
    }

    @Override
    public void setPhoneNumber(String transactionId, String phoneNumber,
                               int... errorCodes) throws ApiResponseException {
        RegisteredUser registeredUser = getEntity(transactionId, errorCodes[0]);
        if (registeredUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }

        if (!registeredUser.getPhoneNumber().equals(phoneNumber)) {
            if (getRegisteredUserMapper().selectByPhoneNumber(phoneNumber) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                registeredUser.setPhoneNumber(phoneNumber);
                setEntity(transactionId, errorCodes[0], registeredUser);
            }
        }
    }

    @Override
    public void setEmailAddress(String transactionId, String emailAddress,
                                int... errorCodes) throws ApiResponseException {

        RegisteredUser registeredUser = getEntity(transactionId, errorCodes[0]);
        if (registeredUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }

        if (!registeredUser.getEmailAddress().equals(emailAddress)) {
            if (getRegisteredUserMapper().selectByEmailAddress(emailAddress) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                registeredUser.setEmailAddress(emailAddress);
                setEntity(transactionId, errorCodes[0], registeredUser);
            }
        }
    }

    @Override
    public void setPassword(String transactionId, String password, String repeatedPassword,
                            int... errorCodes) throws ApiResponseException {

        if (!password.equals(repeatedPassword)) {
            throw new ApiResponseException(errorCodes[0]);
        }

        RegisteredUser registeredUser = getEntity(transactionId, errorCodes[1]);

        if (!getPasswordEncoder().matches(password, registeredUser.getPassword())) {
            registeredUser.setPassword(getPasswordEncoder().encode(password));
            setEntity(transactionId, errorCodes[1], registeredUser);
        }
        else {
            throw new ApiResponseException(errorCodes[2]);
        }

    }

    @Override
    public RegisteredUser reset(String transactionId, int... errorCodes) {
        if (isChanged(transactionId, errorCodes[0])) {
            RegisteredUser registeredUser = getEntity(transactionId, errorCodes[0]);
            getRegisteredUserMapper().updateById(registeredUser);
            return registeredUser;
        }
        else {
            throw new ApiResponseException(errorCodes[1]);
        }
    }

    //***********

    private RegisteredUser getEntity(String transactionId, int errorCode) {
        Object value = getHashOperations(transactionId, errorCode).get("entityJsonStr");
        if (value != null) {
            String json = (String) value;
            return JSON.parseObject(json, RegisteredUser.class);
        }
        else {
            throw new ApiResponseException(errorCode);
        }
    }

    private void setEntity(String transactionId, int errorCode,
                           RegisteredUser registeredUser) {
        getHashOperations(transactionId, errorCode).put("entityJsonStr",
                JSON.toJSONString(registeredUser));
        getHashOperations(transactionId, errorCode).put("isChanged", "1");
    }

    private void setNewEntity(String transactionId,
                              RegisteredUser registeredUser) {
        createHashOperations(transactionId).put("entityJsonStr",
                JSON.toJSONString(registeredUser));
    }


    private boolean isChanged(String transactionId, int errorCode) {
        Object value = getHashOperations(transactionId, errorCode).get("isChanged");
        if (value == null) {
            return false;
        }
        else {
            boolean isChange = (Integer.parseInt((String) value)) == 1;
            return isChange;
        }
    }

}
