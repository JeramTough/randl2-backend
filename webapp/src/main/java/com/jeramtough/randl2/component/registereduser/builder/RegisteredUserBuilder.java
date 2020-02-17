package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 18:03
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserBuilder {

    void rebuildRegisteredUser(@NotNull RegisteredUser registeredUser);

    void resetRegisteredUser();

    void setAccount(String phoneOrEmailOrOther, int... errorCodes) throws ApiResponseException;

    void setPassword(String password, String repeatedPassword,
                     int... errorCodes) throws ApiResponseException;

    RegisteredUser build(int... errorCodes) throws ApiResponseException;
}

