package com.jeramtough.randl2.common.model.detail.userdetail.builder.reset;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2021/1/13 14:48
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class EmailResetUserBuilder extends AbstractResetUserBuilder
        implements ResetUserBuilder {

    protected EmailResetUserBuilder(PasswordEncoder passwordEncoder,
                                    RedisTemplate<String, Object> redisTemplate,
                                    HttpServletRequest httpServletRequest,
                                    LocationGating locationGating) {
        super(passwordEncoder, redisTemplate, httpServletRequest, locationGating);
    }

    @Override
    public void setPhoneOrEmailOrOther(String transactionId, String phoneOrEmailOrOther) throws
            TransactionTimeoutExcaption, AccountFormatException {
        boolean isRightFormat = ValidationUtil.isEmail(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new AccountFormatException("phoneOrEmailOrOther", "例子:11787@qq.com");
        }
        getEntity(transactionId).setEmailAddress(phoneOrEmailOrOther);
    }

    @Override
    public String getResetUserWayForPhoneOrEmail(String transactionId) throws
            TransactionTimeoutExcaption {
        return getEntity(transactionId).getEmailAddress();
    }
}
