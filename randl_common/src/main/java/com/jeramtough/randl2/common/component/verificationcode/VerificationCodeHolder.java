package com.jeramtough.randl2.common.component.verificationcode;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * <pre>
 * 该类负责创建、存储、校验验证码
 * Created on 2020/2/16 22:24
 * by @author JeramTough
 * </pre>
 */
public interface VerificationCodeHolder {


    /**
     * 消费验证码并保存校验结果
     */
    TaskResult consumeCode(String phoneOrEmail,
                           String verificationCode);

    /**
     * 生产并持久化验证码
     */
    String produceAndSaveCode(String phoneOrEmail);

    /**
     * 返回验证码校验结果
     */
    TaskResult getVerificationResult(String phoneOrEmail);

}
