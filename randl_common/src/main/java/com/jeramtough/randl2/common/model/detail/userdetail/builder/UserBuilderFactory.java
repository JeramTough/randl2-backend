package com.jeramtough.randl2.common.model.detail.userdetail.builder;

import com.jeramtough.randl2.common.model.detail.userdetail.RegisterUserWay;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.news.EmailNewUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.news.NewUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.news.PhoneNewUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.reset.EmailResetUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.reset.PhoneResetUserBuilder;
import com.jeramtough.randl2.common.model.detail.userdetail.builder.reset.ResetUserBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 * <pre>
 * Created on 2021/1/14 1:15
 * by @author WeiBoWen
 * </pre>
 */
public class UserBuilderFactory {

    public static NewUserBuilder getNewUserBuilder(WebApplicationContext wc, RegisterUserWay registerUserWay) {
        switch (registerUserWay) {
            case PHONE_USER_WAY:
                return wc.getBean(PhoneNewUserBuilder.class);
            case EMAIL_USER_WAY:
                return wc.getBean(EmailNewUserBuilder.class);
            default:
                return null;
        }
    }

    public static NewUserBuilder getNewUserBuilder(WebApplicationContext wc, int
            way) {
        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(way);
        return getNewUserBuilder(wc, Objects.requireNonNull(registerUserWay));
    }

    public static ResetUserBuilder getResetUserBuilder(WebApplicationContext wc, RegisterUserWay registerUserWay) {
        switch (registerUserWay) {
            case PHONE_USER_WAY:
                return wc.getBean(PhoneResetUserBuilder.class);
            case EMAIL_USER_WAY:
                return wc.getBean(EmailResetUserBuilder.class);
            default:
                return null;
        }
    }

    public static ResetUserBuilder getResetUserBuilder(WebApplicationContext wc, int
            way) {
        RegisterUserWay registerUserWay = RegisterUserWay.toRegisterUserWay(way);
        return getResetUserBuilder(wc, Objects.requireNonNull(registerUserWay));
    }

}
