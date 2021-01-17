package com.jeramtough.authserver.component.userdetail.builder.news;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.authserver.component.userdetail.RegisterUserWay;
import com.jeramtough.authserver.component.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/3/24 16:30
 * by @author JeramTough
 * </pre>
 */
@Component
//@Scope("request")
public class PhoneNewUserBuilder extends AbstractNewUserBuilder
        implements NewUserBuilder {

    @Autowired
    public PhoneNewUserBuilder(
            PasswordEncoder passwordEncoder,
            RedisTemplate<String, Object> redisTemplate,
            HttpServletRequest httpServletRequest) {
        super(passwordEncoder, redisTemplate, httpServletRequest);
    }

    @Override
    public void setAccount(String transactionId, String phoneOrEmailOrOther) throws ApiResponseException,
            TransactionTimeoutExcaption {
        boolean isRightFormat = ValidationUtil.isPhone(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseBeanException(ErrorU.CODE_4.C, "phoneOrEmailOrOther", "例子:15289678163");
        }

        setPhoneNumber(transactionId, phoneOrEmailOrOther);
    }

    @Override
    protected void buildAccount(String transactionId, RandlUser randlUser) throws TransactionTimeoutExcaption{
        String phoneNumber = getPhoneNumber(transactionId);
        randlUser.setPhoneNumber(phoneNumber);
        randlUser.setAccount("P_" + phoneNumber.substring(0,
                Math.min(phoneNumber.length(), 16)));
    }


    @Override
    public RegisterUserWay getRegisterUserWay() {
        return RegisterUserWay.PHONE_USER_WAY;
    }


    //************

    private String getPhoneNumber(String transactionId) throws TransactionTimeoutExcaption {
        return getEntity(transactionId).getPhoneNumber();
    }


    private void setPhoneNumber(String transactionId, String phoneNumber) throws TransactionTimeoutExcaption {
        getEntity(transactionId).setPhoneNumber(phoneNumber);
    }


}
