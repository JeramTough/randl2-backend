package com.jeramtough.randl2.service.resource;

import com.jeramtough.randl2.common.model.params.verificationcode.GetVerifiedResultParams;
import com.jeramtough.randl2.common.model.params.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.ConsumeVerificationCodeParams;

/**
 * <pre>
 * Created on 2020/2/16 21:00
 * by @author JeramTough
 * </pre>
 */
public interface VerificationCodeService {

    /**
     * 生成验证码并以某种方式发送给用户，比如直接返回，通过手机或者邮箱
     */
    String produceVerificationCode(SendVerificationCodeParams params);

    /**
     * 消费验证码，并缓存验证码的校验结果
     */
    String consumeVerificationCode(ConsumeVerificationCodeParams params);

    /**
     * 得到验证码的校验结果
     */
    Boolean getVerifiedResult(GetVerifiedResultParams params);
}
