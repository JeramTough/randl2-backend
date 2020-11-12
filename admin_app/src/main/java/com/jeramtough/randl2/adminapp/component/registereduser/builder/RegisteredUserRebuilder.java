package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.RandlUser;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/3/24 21:34
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserRebuilder extends UserBuilder {

    /**
     * 开始重置账号信息事务
     *
     * @return 事务ID
     */
    String rebuildRegisteredUser(@NotNull RandlUser randlUser);

    void setAccount(String transactionId, String account,
                    int... errorCodes) throws ApiResponseException;

    void setPhoneNumber(String transactionId, String phoneNumber,
                        int... errorCodes) throws ApiResponseException;

    void setEmailAddress(String transactionId, String emailAddress,
                         int... errorCodes) throws ApiResponseException;

    /**
     * 确定重置用户
     */
    RandlUser reset(String transactionId, int... errorCodes);


}
