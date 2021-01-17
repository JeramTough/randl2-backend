package com.jeramtough.randl2.common.component.userdetail.builder.reset;

import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.component.userdetail.builder.CommonUserBuilder;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.AccountFormatException;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.NoChangedException;
import com.jeramtough.randl2.common.component.userdetail.builder.exception.TransactionTimeoutExcaption;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/3/24 21:34
 * by @author JeramTough
 * </pre>
 */
public interface ResetUserBuilder extends CommonUserBuilder {

    /**
     * 设置重置账号对象
     */
    void rebuildRegisteredUser(String transactionId, @NotNull RandlUser randlUser);

    void setPhoneOrEmailOrOther(String transactionId, String phoneOrEmailOrOther) throws
            TransactionTimeoutExcaption, AccountFormatException;

    /**
     * 确定重置用户
     */
    RandlUser reset(String transactionId) throws TransactionTimeoutExcaption, NoChangedException;


}
