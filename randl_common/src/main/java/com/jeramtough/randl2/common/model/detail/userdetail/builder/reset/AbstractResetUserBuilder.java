package com.jeramtough.randl2.common.model.detail.userdetail.builder.reset;

import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.AbstractUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.NoChangedException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <pre>
 * Created on 2021/1/13 14:47
 * by @author WeiBoWen
 * </pre>
 */
public abstract class AbstractResetUserBuilder extends AbstractUserBuilder implements ResetUserBuilder {

    protected AbstractResetUserBuilder(PasswordEncoder passwordEncoder,
                                       RedisTemplate<String, Object> redisTemplate,
                                       HttpServletRequest httpServletRequest,
                                       LocationGating locationGating) {
        super(passwordEncoder, redisTemplate, httpServletRequest, locationGating);
    }

    @Override
    public void rebuildRegisteredUser(String transactionId, @NotNull RandlUser randlUser) {
        setNewEntity(transactionId, randlUser);
    }

    @Override
    public RandlUser reset(String transactionId) throws TransactionTimeoutExcaption, NoChangedException {
        if (isChanged(transactionId)) {
            RandlUser randlUser = getEntity(transactionId);
            randlUser.setModifyTime(new Date());
            setEntity(transactionId, randlUser);
            return randlUser;
        }
        else {
            throw new NoChangedException();
        }
    }


}
