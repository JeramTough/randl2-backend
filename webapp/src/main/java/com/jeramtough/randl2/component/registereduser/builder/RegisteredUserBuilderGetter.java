package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.component.registereduser.RegisterUserWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

/**
 * <pre>
 * Created on 2020/2/16 20:10
 * by @author JeramTough
 * </pre>
 */
@Component
public class RegisteredUserBuilderGetter {

    private final static String REGISTER_USER_WAY_KEY =
            RegisteredUserBuilderGetter.class.getSimpleName() +
                    "registerUserWay";

    private WebApplicationContext webApplicationContext;
    private HttpSession session;


    @Autowired
    public RegisteredUserBuilderGetter(
            WebApplicationContext webApplicationContext,
            HttpSession session) {
        this.webApplicationContext = webApplicationContext;
        this.session = session;
    }

    public void initRegisterUserWay(int way, int... errorCodes) {
        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(way);
        if (registerUserWay == null) {
            throw new ApiResponseException(errorCodes[0]);
        }
        session.setAttribute(REGISTER_USER_WAY_KEY, registerUserWay);
    }

    public RegisteredUserBuilder getRegisteredUserBuilder(int... errorCodes) throws ApiResponseException {
        RegisterUserWay registerUserWay = (RegisterUserWay) session.getAttribute(REGISTER_USER_WAY_KEY);
        if (registerUserWay==null){
            throw new  ApiResponseException(errorCodes[0]);
        }

        switch (registerUserWay) {
            case PHONE_USER_WAY:
                return webApplicationContext.getBean(PhoneRegisteredUserBuilder.class);
            case EMAIL_USER_WAY:
                return webApplicationContext.getBean(EmailRegisteredUserBuilder.class);
            default:
                break;
        }
        return null;
    }
}
