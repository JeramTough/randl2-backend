package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/2/17 15:26
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("session")
public class SessionRegisteredUserBuilder implements RegisteredUserBuilder {
    @Override
    public void setRegisterWay(int way, int... errorCodes) throws ApiResponseException {

    }

    @Override
    public void setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {

    }

    @Override
    public void setPassword(String password, String repeatedPassword, int... errorCodes) throws
            ApiResponseException {

    }

    @Override
    public RegisteredUser build(int... errorCodes) throws ApiResponseException {
        return null;
    }
}
