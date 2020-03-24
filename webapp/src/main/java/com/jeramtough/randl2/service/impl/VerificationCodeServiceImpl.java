package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.component.verificationcode.SessionVerificationCodeHolder;
import com.jeramtough.randl2.component.verificationcode.sender.SendWay;
import com.jeramtough.randl2.component.verificationcode.sender.VerificationCodeSender;
import com.jeramtough.randl2.component.verificationcode.sender.VerificationCodeSenderGetter;
import com.jeramtough.randl2.service.VerificationCodeService;
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
    private final SessionVerificationCodeHolder sessionVerificationCodeHolder;
    private final RedisVerificationCodeHolder redisVerificationCodeHolder;

    @Autowired
    public VerificationCodeServiceImpl(WebApplicationContext wc,
                                       SessionVerificationCodeHolder sessionVerificationCodeHolder,
                                       RedisVerificationCodeHolder redisVerificationCodeHolder) {
        this.wc = wc;
        this.sessionVerificationCodeHolder = sessionVerificationCodeHolder;
        this.redisVerificationCodeHolder = redisVerificationCodeHolder;
    }

    @Override
    public String send(SendVerificationCodeParams params) {
        BeanValidator.verifyDto(params);
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
        int lastSentVerificationCodeInterval = sender.getLastSentVerificationCodeInterval();
        if (lastSentVerificationCodeInterval < maxInterval) {
            int residualInterval = maxInterval - lastSentVerificationCodeInterval;
            throw new ApiResponseException(8000, residualInterval + "");
        }

        String verificationCode =
                sessionVerificationCodeHolder.getAndRecordVerificationCode(
                        params.getPhoneOrEmail());
        boolean isTest = true;
        boolean isSuccessful =
                sender.send(verificationCode, isTest);
        if (!isSuccessful) {
            throw new ApiResponseException(8001, sender.getFailedReason());
        }
        return "验证码" + (isTest ? verificationCode : "") + "以成功发送到【"
                + params.getPhoneOrEmail() + "】,30分钟内有效";
    }

    @Override
    public String verify(String code) {
        if (code == null) {
            throw new ApiResponseException(667, "验证码");
        }
        boolean isPass = sessionVerificationCodeHolder.verifyCode(code);
        if (!isPass) {
            throw new ApiResponseException(8002);
        }
        return "验证码校验成功";
    }

    @Override
    public String registeredUserSendVerificationCode(SendVerificationCodeParams params) {
        BeanValidator.verifyDto(params);
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
        int lastSentVerificationCodeInterval = sender.getLastSentVerificationCodeInterval();
        if (lastSentVerificationCodeInterval < maxInterval) {
            int residualInterval = maxInterval - lastSentVerificationCodeInterval;
            throw new ApiResponseException(8000, residualInterval + "");
        }

        String verificationCode =
                redisVerificationCodeHolder.getAndRecordVerificationCode(
                        params.getPhoneOrEmail());
        boolean isTest = true;
        boolean isSuccessful =
                sender.send(verificationCode, isTest);
        if (!isSuccessful) {
            throw new ApiResponseException(8001, sender.getFailedReason());
        }
        return "验证码" + (isTest ? verificationCode : "") + "以成功发送到【" + params.getPhoneOrEmail() + "】";

    }
}
