package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;

/**
 * <pre>
 * Created on 2020/2/17 15:22
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserBuilder {

    void setRegisterWay(int way, int... errorCodes) throws ApiResponseException;

    void setAccount(String phoneOrEmailOrOther, int... errorCodes) throws ApiResponseException;

    void setPassword(String password, String repeatedPassword,
                     int... errorCodes) throws ApiResponseException;

    RegisteredUser build(int... errorCodes) throws ApiResponseException;
}
