package com.jeramtough.randl2.common.model.detail.userdetail.builder;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2020/3/24 16:07
 * by @author JeramTough
 * </pre>
 */
public abstract class AbstractUserBuilder implements CommonUserBuilder {

    private final static String BUILD_USER_KEY_PREFIX = AbstractUserBuilder.class.getSimpleName() +
            "BuildUser";
    private static final long MAX_EXPIRE_TIME_SECONDS = 15 * 60;

    public static final Long DEFAULT_SURFACE_IMAGE_ID = 2L;

    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HttpServletRequest httpServletRequest;
    private final LocationGating locationGating;

    protected AbstractUserBuilder(
            PasswordEncoder passwordEncoder,
            RedisTemplate<String, Object> redisTemplate, HttpServletRequest httpServletRequest,
            LocationGating locationGating) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
        this.httpServletRequest = httpServletRequest;
        this.locationGating = locationGating;
    }

    @Override
    public String start() {
        return createTransactionId();
    }

    @Override
    public void setPassword(String transactionId, String password) throws TransactionTimeoutExcaption {
        RandlUser randlUser = getEntity(transactionId);
        password=passwordEncoder.encode(password);
        randlUser.setPassword(password);
        setEntity(transactionId, randlUser);
    }

    @Override
    public void clear(String transactionId) {
        redisTemplate.delete(getRegisteredUserKey(transactionId));
    }


    protected BoundHashOperations<String, String, Object> createHashOperations(
            String transactionId) {
        BoundHashOperations<String, String, Object> hashOperations =
                redisTemplate.boundHashOps(getRegisteredUserKey(transactionId));
        hashOperations.expire(MAX_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
        return hashOperations;
    }

    protected BoundHashOperations<String, String, Object> getHashOperations(
            String transactionId) throws TransactionTimeoutExcaption {
        Boolean isHasKey = redisTemplate.hasKey(getRegisteredUserKey(transactionId));
        if (isHasKey == null || !isHasKey) {
            throw new TransactionTimeoutExcaption();
        }
        BoundHashOperations<String, String, Object> hashOperations =
                redisTemplate.boundHashOps(getRegisteredUserKey(transactionId));
        return hashOperations;
    }

    protected String getRegisteredUserKey(String transactionId) {
        return BUILD_USER_KEY_PREFIX + "_" + transactionId;
    }

    protected String getPassword(String transactionId) throws TransactionTimeoutExcaption {
        return getEntity(transactionId).getPassword();
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    protected RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    protected void setNewEntity(String transactionId,
                                RandlUser randlUser) {
        createHashOperations(transactionId).put("entityJsonStr",
                JSON.toJSONString(randlUser));
    }

    protected RandlUser getEntity(String transactionId) throws TransactionTimeoutExcaption {
        Object value = getHashOperations(transactionId).get("entityJsonStr");
        Objects.requireNonNull(value);
        String json = (String) value;
        return JSON.parseObject(json, RandlUser.class);
    }

    protected void setEntity(String transactionId,
                             RandlUser randlUser)
            throws TransactionTimeoutExcaption {
        getHashOperations(transactionId).put("entityJsonStr",
                JSON.toJSONString(randlUser));
        getHashOperations(transactionId).put("isChanged", "1");
    }

    protected boolean isChanged(String transactionId) {
        Object value;
        try {
            value = getHashOperations(transactionId).get("isChanged");
        }
        catch (TransactionTimeoutExcaption transactionTimeoutExcaption) {
            return false;
        }
        if (value == null) {
            return false;
        }
        else {
            boolean isChange = (Integer.parseInt((String) value)) == 1;
            return isChange;
        }
    }


    protected LocationGating getLocationGating(){
        return locationGating;
    }

    //***************************

    private String createTransactionId() {
        return IdUtil.getUUID();
    }


}
