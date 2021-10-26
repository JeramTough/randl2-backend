package com.jeramtough.randl2.common.model.detail.userdetail.builder.reset;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2021/1/13 14:48
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class PhoneResetUserBuilder extends AbstractResetUserBuilder
        implements ResetUserBuilder {

    protected PhoneResetUserBuilder(PasswordEncoder passwordEncoder,
                                    RedisTemplate<String, Object> redisTemplate,
                                    HttpServletRequest httpServletRequest,
                                    LocationGating locationGating) {
        super(passwordEncoder, redisTemplate, httpServletRequest, locationGating);
    }

    @Override
    public void setPhoneOrEmailOrOther(String transactionId, String phoneOrEmailOrOther) throws
            TransactionTimeoutExcaption {
        boolean isRightFormat = ValidationUtil.isPhone(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseBeanException(ErrorU.CODE_4.C, "phoneOrEmailOrOther",
                    "例子:15289678163");
        }
        getEntity(transactionId).setPhoneNumber(phoneOrEmailOrOther);
    }

    @Override
    public String getResetUserWayForPhoneOrEmail(String transactionId) throws
            TransactionTimeoutExcaption {
        return getEntity(transactionId).getPhoneNumber();
    }
}
