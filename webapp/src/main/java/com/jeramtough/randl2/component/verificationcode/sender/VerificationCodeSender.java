package com.jeramtough.randl2.component.verificationcode.sender;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2018-09-13 00:21
 * by @author JeramTough
 */
public interface VerificationCodeSender {

    TaskResult send(String phoneOrEmail, String verificationCode, boolean isTest);

    int getLastSentVerificationCodeInterval(String phoneOrEmail);
}
