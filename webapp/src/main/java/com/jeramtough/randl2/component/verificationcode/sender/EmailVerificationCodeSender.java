package com.jeramtough.randl2.component.verificationcode.sender;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/24 14:41
 * by @author JeramTough
 * </pre>
 */
@Component
public class EmailVerificationCodeSender extends BaseVerificationCodeSender {

    @Autowired
    public EmailVerificationCodeSender(
            RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public TaskResult doSending(String phoneOrEmail, String verificationCode) {
        return ResponseFactory.doing(preTaskResult -> {
            preTaskResult.setMessage("还没写实现");
            return false;
        }).getTaskResult();
    }
}
