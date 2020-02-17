package com.jeramtough.randl2.component.verificationcode.sender;

/**
 * Created on 2018-09-13 00:21
 * by @author JeramTough
 */
public interface VerificationCodeSender {

    boolean send(String verificationCode, boolean isTest);

    String getFailedReason();

    int getLastSentVerificationCodeInterval();
}
