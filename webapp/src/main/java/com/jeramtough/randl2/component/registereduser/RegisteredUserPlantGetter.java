package com.jeramtough.randl2.component.registereduser;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/2/16 20:10
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("session")
public class RegisteredUserPlantGetter {


    private WebApplicationContext webApplicationContext;

    private RegisterUserWay registerUserWay;

    @Autowired
    public RegisteredUserPlantGetter(
            WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    public RegisterUserWay getRegisterUserWay() {
        return registerUserWay;
    }

    public void setRegisterUserWay(
            RegisterUserWay registerUserWay) {
        this.registerUserWay = registerUserWay;
    }

    public RegisteredUserPlant getRegisteredUserPlant(int errorCode) throws ApiResponseException {
        if (registerUserWay == null) {
            throw new ApiResponseException(errorCode);
        }
        switch (registerUserWay) {
            case PHONE_USER_WAY:
                return webApplicationContext.getBean(PhoneRegisteredUserPlant.class);
            case EMAIL_USER_WAY:
                return webApplicationContext.getBean(EmailRegisteredUserPlant.class);
            default:
                break;
        }
        return null;
    }
}
