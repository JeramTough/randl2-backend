package com.jeramtough.randl2.component.verificationcode;

import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtcomponent.task.runner.SimpleRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2020/3/23 22:50
 * by @author JeramTough
 * </pre>
 */
@Component
public class RedisVerificationCodeHolder implements VerificationCodeHolder {

    private static final String VERIFICATION_CODES_KEY_PREFIX =
            RedisVerificationCodeHolder.class.getSimpleName() + "_CODE";

    private static final int MAX_CAPACITY = 3;

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisVerificationCodeHolder(
            RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Deprecated
    @Override
    public boolean verifyCode(String verificationCode) {
        return false;
    }

    @Override
    public TaskResult verifyCode(final String phoneOrEmail,
                                 final String verificationCode) {
        return ResponseFactory.doing(preTaskResult -> {
            BoundListOperations listOperations =
                    redisTemplate.boundListOps(getVerificationCodesKey(phoneOrEmail));
            if (listOperations==null||listOperations.size()==0){
                preTaskResult.setMessage("验证码未发送或者以失效");
                return false;
            }
            List<String> verificationCodeList=listOperations.range(0,-1);
            boolean isPassed = false;
            for (String rightVerificationCode : verificationCodeList) {
                if (rightVerificationCode.equalsIgnoreCase(verificationCode)) {
                    isPassed = true;
                    break;
                }
            }
            preTaskResult.setMessage("验证码不正确！");
            return isPassed;
        }).getTaskResult();
    }

    @Override
    public String getAndRecordVerificationCode(String phoneOrEmail) {
        String verificationCode = RandomStringUtils.random(6, false, true);

        BoundListOperations listOperations =
                redisTemplate.boundListOps(getVerificationCodesKey(phoneOrEmail));
        listOperations.expire(30 * 60, TimeUnit.SECONDS);

        if (listOperations.size() == MAX_CAPACITY) {
            listOperations.leftPop();
        }
        listOperations.rightPush(verificationCode);
        return verificationCode;
    }

    @Deprecated
    @Override
    public SessionVerificationCodeHolder.VerificationResult getVerificationResult() {
        return null;
    }

    //*************

    private String getVerificationCodesKey(String phoneOrEmail) {
        String verificationCodesKey = VERIFICATION_CODES_KEY_PREFIX + "_" + phoneOrEmail;
        return verificationCodesKey;
    }
}
