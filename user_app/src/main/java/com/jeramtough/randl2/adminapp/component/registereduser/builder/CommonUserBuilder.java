package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.userdetail.RegisteredUserRole;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2020/3/24 16:07
 * by @author JeramTough
 * </pre>
 */
public abstract class CommonUserBuilder implements UserBuilder {

    private final static String REGISTERED_USER_KEY_PREFIX = CommonUserBuilder.class.getSimpleName() +
            "registeredUser";
    private static final long MAX_EXPIRE_TIME_SECONDS = 15 * 60;

    private final PasswordEncoder passwordEncoder;
    private final RegisteredUserMapper registeredUserMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    protected CommonUserBuilder(
            PasswordEncoder passwordEncoder,
            RegisteredUserMapper registeredUserMapper,
            RedisTemplate<String, Object> redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.registeredUserMapper = registeredUserMapper;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void setPassword(String transactionId, String password,
                            String repeatedPassword, int... errorCodes) throws
            ApiResponseException {
        if (!password.equals(repeatedPassword)) {
            throw new ApiResponseException(errorCodes[0]);
        }
        getHashOperations(transactionId, errorCodes[1]).put("password", password);
    }

    @Override
    public void clear(String transactionId) {
        redisTemplate.delete(getRegisteredUserKey(transactionId));
    }

    protected RegisteredUser buildTheCommon(){
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setRegistrationTime(LocalDateTime.now());
        registeredUser.setAccountStatus(1);
        registeredUser.setSurfaceImageId(2L);
        registeredUser.setRoleId(RegisteredUserRole.PrimaryRole.get().getFid());
        return registeredUser;
    }

    protected BoundHashOperations<String, String, Object> createHashOperations(
            String transactionId) {
        BoundHashOperations<String, String, Object> hashOperations =
                redisTemplate.boundHashOps(getRegisteredUserKey(transactionId));
        hashOperations.expire(MAX_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
        return hashOperations;
    }

    protected BoundHashOperations<String, String, Object> getHashOperations(
            String transactionId, int errorCode) {
        Boolean isHasKey = redisTemplate.hasKey(getRegisteredUserKey(transactionId));
        if (isHasKey == null || !isHasKey) {
            throw new ApiResponseException(errorCode);
        }
        BoundHashOperations<String, String, Object> hashOperations =
                redisTemplate.boundHashOps(getRegisteredUserKey(transactionId));
        return hashOperations;
    }

    protected String getRegisteredUserKey(String transactionId) {
        return REGISTERED_USER_KEY_PREFIX + "_" + transactionId;
    }

    protected String getPassword(String transactionId, int errorCode) {
        return (String) getHashOperations(transactionId, errorCode).get("password");
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    protected RegisteredUserMapper getRegisteredUserMapper() {
        return registeredUserMapper;
    }

    protected RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    protected String createTransactionId() {
        return IdUtil.getUUID();
    }


}
