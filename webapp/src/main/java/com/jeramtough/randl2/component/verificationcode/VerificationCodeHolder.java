package com.jeramtough.randl2.component.verificationcode;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 该类负责创建、存储、校验验证码
 * Created on 2020/2/16 22:24
 * by @author JeramTough
 * </pre>
 */
public interface VerificationCodeHolder {

//    boolean verifySign(String sign);

    boolean verifyCode(String verificationCode);

    TaskResult verifyCode(String phoneOrEmail,
                          String verificationCode);

    String getAndRecordVerificationCode(String phoneOrEmail);

    /**
     * 返回验证码校验结果
     */
    boolean getVerificationResult(String phoneOrEmail);

}
