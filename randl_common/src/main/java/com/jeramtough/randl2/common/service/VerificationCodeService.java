package com.jeramtough.randl2.common.service;

import com.jeramtough.randl2.common.model.params.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.VerifyVerificationCodeParams;

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

    String verify(VerifyVerificationCodeParams params);
}
