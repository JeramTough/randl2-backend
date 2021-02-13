package com.jeramtough.randl2.common.component.userdetail.builder.news;

import com.jeramtough.randl2.common.component.userdetail.RegisterUserWay;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.component.userdetail.builder.CommonUserBuilder;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.NotSetPasswordException;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.TransactionTimeoutExcaption;

/**
 * <pre>
 * Created on 2020/2/16 18:03
 * by @author JeramTough
 * </pre>
 */
public interface NewUserBuilder extends CommonUserBuilder {

    /**
     * 开始注册新用户事务
     */
    void setAccount(String transactionId, String phoneOrEmailOrOther) throws AccountFormatException,
            TransactionTimeoutExcaption;


    /**
     * 确定构建新用户
     */
    RandlUser build(String transactionId) throws TransactionTimeoutExcaption, NotSetPasswordException;


    RegisterUserWay getRegisterUserWay();

    String getRegisterUserWayForPhoneOrEmail(String transactionId) throws TransactionTimeoutExcaption;
}

