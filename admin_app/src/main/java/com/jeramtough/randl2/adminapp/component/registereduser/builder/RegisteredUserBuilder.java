package com.jeramtough.randl2.adminapp.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.adminapp.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.common.model.entity.RandlUser;

/**
 * <pre>
 * Created on 2020/2/16 18:03
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserBuilder extends UserBuilder {

    /**
     * 开始注册新用户事务
     *
     * @return 事务ID
     */
    String setAccount(String phoneOrEmailOrOther,
                      int... errorCodes) throws ApiResponseException;


    /**
     * 确定构建新用户
     */
    RandlUser build(String transactionId, int... errorCodes) throws
            ApiResponseException;


    RegisterUserWay getRegisterUserWay();

}

