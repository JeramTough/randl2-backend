package com.jeramtough.randl2.common.component.verificationcode;

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
@Component("redisVerificationCodeHolder")
public class RedisVerificationCodeHolder implements VerificationCodeHolder {

    private static final String VERIFICATION_CODES_KEY_PREFIX =
            RedisVerificationCodeHolder.class.getSimpleName() + "_CODE";
    private static final String VERIFICATION_RESULT_KEY_PREFIX =
            RedisVerificationCodeHolder.class.getSimpleName() + "_RESULT";

    private static final long MAX_EXPIRE_TIME_SECONDS = 30 * 60;

    private static final int MAX_CAPACITY = 3;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisVerificationCodeHolder(
            RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public TaskResult consumeCode(final String phoneOrEmail,
                                  final String verificationCode) {
        return ResponseFactory.doing(preTaskResult -> {
            BoundListOperations listOperations =
                    redisTemplate.boundListOps(getVerificationCodesKey(phoneOrEmail));

            if (listOperations == null || listOperations.size() == 0) {
                preTaskResult.setMessage("验证码未发送或者以失效");
                return false;
            }

            List<String> verificationCodeList = listOperations.range(0, -1);
            boolean isPassed = false;
            preTaskResult.setMessage("验证码不正确！");
            for (String rightVerificationCode : verificationCodeList) {
                if (rightVerificationCode.equalsIgnoreCase(verificationCode)) {
                    isPassed = true;
                    preTaskResult.setMessage("验证码正确");

                    //校验成功后将缓存的所有校验码清除掉
                    redisTemplate.delete(getVerificationCodesKey(phoneOrEmail));
                    break;
                }
            }

            //保存校验结果
            redisTemplate.boundValueOps(getVerificationResultKey(phoneOrEmail))
                         .set(isPassed, MAX_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
            return isPassed;
        }).getTaskResult();
    }

    @Override
    public String produceAndSaveCode(String phoneOrEmail) {
        String verificationCode = RandomStringUtils.random(6, false, true);

        BoundListOperations listOperations =
                redisTemplate.boundListOps(getVerificationCodesKey(phoneOrEmail));
        listOperations.expire(MAX_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);

        if (listOperations.size() == MAX_CAPACITY) {
            listOperations.leftPop();
        }
        listOperations.rightPush(verificationCode);
        return verificationCode;
    }

    @Override
    public TaskResult getVerificationResult(String phoneOrEmail) {
        return ResponseFactory.doing(preTaskResult -> {
            Boolean isPassed =
                    (Boolean) redisTemplate.boundValueOps(getVerificationResultKey(phoneOrEmail))
                                           .get();
            if (isPassed == null) {
                preTaskResult.setMessage("验证码未发送或者以失效");
                return false;
            }

            if (!isPassed) {
                preTaskResult.setMessage("验证码不正确！");
                return false;
            }
            else {
                return true;
            }
        }).getTaskResult();

    }

    //*************

    private String getVerificationCodesKey(String phoneOrEmail) {
        String verificationCodesKey = VERIFICATION_CODES_KEY_PREFIX + "_" + phoneOrEmail;
        return verificationCodesKey;
    }

    private String getVerificationResultKey(String phoneOrEmail) {
        String key = VERIFICATION_RESULT_KEY_PREFIX + "_" + phoneOrEmail;
        return key;
    }
}
