package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.verificationcode.SendVerificationCodeParams;

/**
 * <pre>
 * Created on 2020/2/16 21:00
 * by @author JeramTough
 * </pre>
 */
public interface VerificationCodeService {

    /**
     * 发送邮箱或者手机验证码
     */
    String send(SendVerificationCodeParams params);

    String verify(String code);

    /**
     * 普通注册用户发送短信验证码
     */
    String registeredUserSendVerificationCode(SendVerificationCodeParams params);
}
