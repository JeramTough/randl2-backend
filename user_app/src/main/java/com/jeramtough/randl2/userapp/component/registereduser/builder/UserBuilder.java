package com.jeramtough.randl2.userapp.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;

/**
 * <pre>
 * Created on 2020/2/16 18:03
 * by @author JeramTough
 * </pre>
 */
public interface UserBuilder {


    void setPassword(String transactionId, String password, String repeatedPassword,
                     int... errorCodes) throws ApiResponseException;



    void clear(String transactionId);


}

