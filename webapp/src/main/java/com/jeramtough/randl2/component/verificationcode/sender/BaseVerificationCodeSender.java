package com.jeramtough.randl2.component.verificationcode.sender
        ;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.jtlog.with.WithLogger;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <pre>
 * Created on 2020/3/24 14:27
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseVerificationCodeSender implements VerificationCodeSender,
        WithLogger {

    private static final String LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY_PROFIX =
            "lastSentVerificationCodeTime";

    private RedisTemplate<String, Object> redisTemplate;

    public BaseVerificationCodeSender(
            RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public TaskResult send(String phoneOrEmail, String verificationCode, boolean isTest) {
        TaskResult taskResult = null;
        if (isTest) {
            taskResult = new TaskResult();
            taskResult.setSuccessful(true);
        }
        else {
            //sending option.
            taskResult = doSending(phoneOrEmail, verificationCode);
        }

        //however true or false, saving the last sent verification code time to session
        String key = LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY_PROFIX + "_" + phoneOrEmail;
        redisTemplate.boundValueOps(key).set(System.currentTimeMillis() + "");

        if (taskResult.isSuccessful()) {
            if (isTest) {
                getLogger().verbose(
                        "Sending the verification code【" + verificationCode + "】 successfully at " + DateTimeUtil.getCurrentDateTime());
            }
            else {
                getLogger().verbose(
                        "Sending the verification code successfully at " + DateTimeUtil.getCurrentDateTime());
            }
        }
        else {
            getLogger().warn("Sending the verification code unsuccessfully");
        }
        return taskResult;
    }

    @Override
    public int getLastSentVerificationCodeInterval(String phoneOrEmail) {
        String key = LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY_PROFIX + "_" + phoneOrEmail;
        Object value = redisTemplate.boundValueOps(key).get();
        if (value == null) {
            return Integer.MAX_VALUE;
        }
        else {
            long lastSentVerificationCodeTime = Long.parseLong((String) value);
            return Math.toIntExact(
                    (System.currentTimeMillis() - lastSentVerificationCodeTime) / 1000);
        }
    }

    public abstract TaskResult doSending(String phoneOrEmail, String verificationCode);
}
