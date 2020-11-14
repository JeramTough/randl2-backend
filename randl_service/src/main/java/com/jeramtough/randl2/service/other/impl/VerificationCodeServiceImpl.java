package com.jeramtough.randl2.service.other.impl;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.VerifyVerificationCodeParams;
import com.jeramtough.randl2.common.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.common.component.verificationcode.sender.SendWay;
import com.jeramtough.randl2.common.component.verificationcode.sender.VerificationCodeSender;
import com.jeramtough.randl2.common.component.verificationcode.sender.VerificationCodeSenderGetter;
import com.jeramtough.randl2.service.other.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/2/16 21:09
 * by @author JeramTough
 * </pre>
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final WebApplicationContext wc;
    private final RedisVerificationCodeHolder redisVerificationCodeHolder;

    @Autowired
    public VerificationCodeServiceImpl(WebApplicationContext wc,
                                       RedisVerificationCodeHolder redisVerificationCodeHolder) {
        this.wc = wc;
        this.redisVerificationCodeHolder = redisVerificationCodeHolder;
    }

    @Override
    public String send(SendVerificationCodeParams params) {
        BeanValidator.verifyParams(params);

        if (SendWay.getSendWay(params.getWay()) == SendWay.PHONE) {
            if (!ValidationUtil.isPhone(params.getPhoneOrEmail())) {
                throw new ApiResponseException(668, "手机号码", "11位手机号码格式");
            }
        }
        else if (SendWay.getSendWay(params.getWay()) == SendWay.EMAIL) {
            if (!ValidationUtil.isEmail(params.getPhoneOrEmail())) {
                throw new ApiResponseException(668, "邮箱地址", "标准邮箱格式");
            }
        }
        VerificationCodeSender sender = VerificationCodeSenderGetter.getSender(wc,
                params.getWay());

        int maxInterval = 60;
        int lastSentVerificationCodeInterval =
                sender.getLastSentVerificationCodeInterval(params.getPhoneOrEmail());
        if (lastSentVerificationCodeInterval < maxInterval) {
            int residualInterval = maxInterval - lastSentVerificationCodeInterval;
            throw new ApiResponseException(ErrorU.CODE_402.C, residualInterval + "");
        }

        String verificationCode =
                redisVerificationCodeHolder.getAndRecordVerificationCode(
                        params.getPhoneOrEmail());
        boolean isTest = true;
        TaskResult taskResult =
                sender.send(params.getPhoneOrEmail(), verificationCode, isTest);
        if (!taskResult.isSuccessful()) {
            throw new ApiResponseException(ErrorU.CODE_403.C, taskResult.getMessage());
        }
        return "验证码" + (isTest ? verificationCode : "") + "以成功发送到【"
                + params.getPhoneOrEmail() + "】,30分钟内有效";
    }

    @Override
    public String verify(VerifyVerificationCodeParams params) {

        BeanValidator.verifyParams(params);

        TaskResult taskResult = redisVerificationCodeHolder.verifyCode(
                params.getPhoneOrEmail(),
                params.getVerificationCode());
        if (!taskResult.isSuccessful()) {
            throw new ApiResponseException(ErrorU.CODE_404.C, taskResult.getMessage());
        }
        return "验证码校验成功";
    }

}
