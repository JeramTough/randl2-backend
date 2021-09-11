package com.jeramtough.randl2.common.model.detail.userdetail.builder.news;

import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.common.component.attestation.userdetail.AccountStatus;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserChannel;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.AbstractUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.NotSetPasswordException;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <pre>
 * Created on 2020/3/24 16:07
 * by @author JeramTough
 * </pre>
 */
public abstract class AbstractNewUserBuilder extends AbstractUserBuilder implements NewUserBuilder {


    protected AbstractNewUserBuilder(PasswordEncoder passwordEncoder,
                                     RedisTemplate<String, Object> redisTemplate,
                                     HttpServletRequest httpServletRequest) {
        super(passwordEncoder, redisTemplate, httpServletRequest);
    }

    @Override
    public String start() {
        String transactionId = super.start();
        RandlUser randlUser = new RandlUser();
        setNewEntity(transactionId, randlUser);
        return transactionId;
    }

    @Override
    public RandlUser build(String transactionId) throws TransactionTimeoutExcaption, NotSetPasswordException {
        RandlUser randlUser = getEntity(transactionId);
        randlUser.setRegistrationTime(new Date());
        randlUser.setAccountStatus(AccountStatus.ABLE.getNumber());
        randlUser.setSurfaceImageId(DEFAULT_SURFACE_IMAGE_ID);
        randlUser.setChannel(UserChannel.REGISTERED.getCode());

        String ipAddress = IpAddrUtil.getIpAddr(getHttpServletRequest());
        randlUser.setRegistrationIp(ipAddress);

        //校验是否设置了密码
        String password = getPassword(transactionId);
        if (password == null) {
            throw new NotSetPasswordException();
        }

        //设置不同类型的注册默认账号
        buildAccount(transactionId, randlUser);

        return randlUser;
    }

    protected abstract void buildAccount(String transactionId, RandlUser randlUser) throws TransactionTimeoutExcaption;


}
