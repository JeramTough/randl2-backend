package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
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
            RandlUserMapper randlUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        super(passwordEncoder, randlUserMapper, redisTemplate);
    }

    @Override
    public String rebuildRegisteredUser(@NotNull RandlUser randlUser) {
        String transactionId = createTransactionId();
        setNewEntity(transactionId, randlUser);
        return transactionId;
    }

    @Override
    public void setAccount(String transactionId, String account, int... errorCodes) throws
            ApiResponseException {
        RandlUser randlUser = getEntity(transactionId, errorCodes[0]);
        if (randlUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }
        if (!randlUser.getAccount().equals(account)) {
            if (getRandlUserMapper().selectByAccount(account) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                randlUser.setAccount(account);
                setEntity(transactionId, errorCodes[0], randlUser);
            }
        }
    }

    @Override
    public void setPhoneNumber(String transactionId, String phoneNumber,
                               int... errorCodes) throws ApiResponseException {
        RandlUser randlUser = getEntity(transactionId, errorCodes[0]);
        if (randlUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }

        if (!randlUser.getPhoneNumber().equals(phoneNumber)) {
            if (getRandlUserMapper().selectByPhoneNumber(phoneNumber) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                randlUser.setPhoneNumber(phoneNumber);
                setEntity(transactionId, errorCodes[0], randlUser);
            }
        }
    }

    @Override
    public void setEmailAddress(String transactionId, String emailAddress,
                                int... errorCodes) throws ApiResponseException {

        RandlUser randlUser = getEntity(transactionId, errorCodes[0]);
        if (randlUser == null) {
            throw new ApiResponseException(errorCodes[0]);
        }

        if (!randlUser.getEmailAddress().equals(emailAddress)) {
            if (getRandlUserMapper().selectByEmailAddress(emailAddress) != null) {
                throw new ApiResponseException(errorCodes[1]);
            }
            else {
                randlUser.setEmailAddress(emailAddress);
                setEntity(transactionId, errorCodes[0], randlUser);
            }
        }
    }

    @Override
    public void setPassword(String transactionId, String password, String repeatedPassword,
                            int... errorCodes) throws ApiResponseException {

        if (!password.equals(repeatedPassword)) {
            throw new ApiResponseException(errorCodes[0]);
        }

        RandlUser randlUser = getEntity(transactionId, errorCodes[1]);

        if (!getPasswordEncoder().matches(password, randlUser.getPassword())) {
            randlUser.setPassword(getPasswordEncoder().encode(password));
            setEntity(transactionId, errorCodes[1], randlUser);
        }
        else {
            throw new ApiResponseException(errorCodes[2]);
        }

    }

    @Override
    public RandlUser reset(String transactionId, int... errorCodes) {
        if (isChanged(transactionId, errorCodes[0])) {
            RandlUser randlUser = getEntity(transactionId, errorCodes[0]);
            getRandlUserMapper().updateById(randlUser);
            return randlUser;
        }
        else {
            throw new ApiResponseException(errorCodes[1]);
        }
    }

    //***********

    private RandlUser getEntity(String transactionId, int errorCode) {
        Object value = getHashOperations(transactionId, errorCode).get("entityJsonStr");
        if (value != null) {
            String json = (String) value;
            return JSON.parseObject(json, RandlUser.class);
        }
        else {
            throw new ApiResponseException(errorCode);
        }
    }

    private void setEntity(String transactionId, int errorCode,
                           RandlUser randlUser) {
        getHashOperations(transactionId, errorCode).put("entityJsonStr",
                JSON.toJSONString(randlUser));
        getHashOperations(transactionId, errorCode).put("isChanged", "1");
    }

    private void setNewEntity(String transactionId,
                              RandlUser randlUser) {
        createHashOperations(transactionId).put("entityJsonStr",
                JSON.toJSONString(randlUser));
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
