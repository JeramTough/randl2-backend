package com.jeramtough.randl2.common.model.detail.userdetail.builder.news;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.randl2.common.model.detail.userdetail.RegisterUserWay;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import com.jeramtough.randl2.common.model.entity.RandlUser;
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
public class EmailNewUserBuilder extends AbstractNewUserBuilder
        implements NewUserBuilder {

    @Autowired
    public EmailNewUserBuilder(
            PasswordEncoder passwordEncoder,
            RedisTemplate<String, Object> redisTemplate,
            HttpServletRequest httpServletRequest) {
        super(passwordEncoder, redisTemplate, httpServletRequest);
    }


    @Override
    public void setAccount(String transactionId, String phoneOrEmailOrOther) throws AccountFormatException,
            TransactionTimeoutExcaption {
        boolean isRightFormat = ValidationUtil.isEmail(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new AccountFormatException("phoneOrEmailOrOther", "例子:11787@qq.com");
        }
        setEmailAddress(transactionId, phoneOrEmailOrOther);
    }

    @Override
    protected void buildAccount(String transactionId, RandlUser randlUser) throws TransactionTimeoutExcaption {
        String emailAddress = getEmailAddress(transactionId);
        randlUser.setEmailAddress(emailAddress);
        randlUser.setAccount("E_" + emailAddress.substring(0,
                Math.min(emailAddress.length(), 16)));
    }

    @Override
    public RegisterUserWay getRegisterUserWay() {
        return RegisterUserWay.EMAIL_USER_WAY;
    }

    @Override
    public String getRegisterUserWayForPhoneOrEmail(String transactionId) throws TransactionTimeoutExcaption {
        return getEmailAddress(transactionId);
    }

    //************

    private String getEmailAddress(String transactionId) throws TransactionTimeoutExcaption {
        return getEntity(transactionId).getEmailAddress();
    }


    private void setEmailAddress(String transactionId, String emailAddress) throws TransactionTimeoutExcaption {
        RandlUser randlUser = getEntity(transactionId);
        randlUser.setEmailAddress(emailAddress);
        setEntity(transactionId,randlUser);
    }


}
