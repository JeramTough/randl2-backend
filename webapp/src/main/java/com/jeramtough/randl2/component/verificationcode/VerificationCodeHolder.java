package com.jeramtough.randl2.component.verificationcode;

/**
 * <pre>
 * Created on 2020/2/16 22:24
 * by @author JeramTough
 * </pre>
 */
public interface VerificationCodeHolder {

//    boolean verifySign(String sign);

    boolean verifyCode(String verificationCode);

    String getAndRecordVerificationCode(String phoneOrEmail);

    SessionVerificationCodeHolder.VerificationResult getVerificationResult();

}
