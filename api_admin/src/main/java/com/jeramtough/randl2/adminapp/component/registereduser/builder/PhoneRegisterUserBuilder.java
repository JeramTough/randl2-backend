package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.adminapp.component.registereduser.RegisterUserWay;
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
public class PhoneRegisterUserBuilder extends CommonUserBuilder
        implements RegisteredUserBuilder {

    @Autowired
    public PhoneRegisterUserBuilder(
            PasswordEncoder passwordEncoder,
            RandlUserMapper randlUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        super(passwordEncoder, randlUserMapper, redisTemplate);
    }


    @Override
    public String setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {
        boolean isRightFormat = ValidationUtil.isPhone(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[0], "手机号码格式", "例子:15289678164");
        }
        if (getRandlUserMapper().selectByPhoneNumber(phoneOrEmailOrOther) != null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        String transactionId = createTransactionId();
        setPhoneNumber(transactionId, phoneOrEmailOrOther);
        return transactionId;
    }


    @Override
    public RandlUser build(String transactionId, int... errorCodes) throws
            ApiResponseException {
        String phoneNumber = getPhoneNumber(transactionId, errorCodes[0]);
        String password = getPassword(transactionId, errorCodes[0]);
        if (phoneNumber == null || password == null) {
            throw new ApiResponseException(errorCodes[1]);
        }

        //构建对象公共部门
        RandlUser randlUser = buildTheCommon();

        randlUser.setPhoneNumber(phoneNumber);
        randlUser.setPassword(getPasswordEncoder().encode(password));
        randlUser.setAccount("P_" + phoneNumber);
        return randlUser;
    }

    @Override
    public RegisterUserWay getRegisterUserWay() {
        return RegisterUserWay.PHONE_USER_WAY;
    }

    //************

    private String getPhoneNumber(String transactionId, int errorCode) {
        return (String) getHashOperations(transactionId, errorCode).get("phoneNumber");
    }


    private void setPhoneNumber(String transactionId, String phoneNumber) {
        createHashOperations(transactionId).put("phoneNumber", phoneNumber);
    }


}
