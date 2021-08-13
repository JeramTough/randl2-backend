package com.jeramtough.randl2.common.model.detail.userdetail.builder;


import com.jeramtough.randl2.common.model.detail.userdetail.builder.exception.TransactionTimeoutExcaption;

/**
 * <pre>
 * Created on 2020/2/16 18:03
 * by @author JeramTough
 * </pre>
 */
public interface CommonUserBuilder {


    /**
     * 开始构建用户事务
     *
     * @return 事务Id
     */
    String start();

    void setPassword(String transactionId, String password) throws TransactionTimeoutExcaption;


    void clear(String transactionId);


}

