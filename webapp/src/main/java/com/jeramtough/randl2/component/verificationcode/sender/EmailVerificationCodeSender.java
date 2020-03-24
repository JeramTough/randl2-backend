package com.jeramtough.randl2.component.verificationcode.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created on 2018-09-13 01:20
 * by @author JeramTough
 */
@Component
public class EmailVerificationCodeSender extends BaseVerificationCodeSender
        implements VerificationCodeSender {

    @Autowired
    protected EmailVerificationCodeSender(HttpSession httpSession) {
        super(httpSession, redisTemplate);
    }

    @Override
    public boolean doSending(String verificationCode) {
        return false;
    }

    @Override
    public String getFailedReason() {
        return "还没写实现";
    }


}
