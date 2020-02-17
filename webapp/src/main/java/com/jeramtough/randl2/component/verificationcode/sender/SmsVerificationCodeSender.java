package com.jeramtough.randl2.component.verificationcode.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created on 2018-09-13 00:22
 * by @author JeramTough
 */
@Component
public class SmsVerificationCodeSender extends BaseVerificationCodeSender {

    @Autowired
    protected SmsVerificationCodeSender(HttpSession httpSession) {
        super(httpSession);
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
