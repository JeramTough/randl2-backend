package com.jeramtough.randl2.component.registereduser;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/2/16 18:54
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("session")
public class EmailRegisteredUserPlant extends BaseRegisteredUserPlant {


    @Autowired
    public EmailRegisteredUserPlant(HttpSession session,
                                    PasswordEncoder passwordEncoder,
                                    RegisteredUserMapper registeredUserMapper) {
        super(session, passwordEncoder, registeredUserMapper);
    }

    @Override
    public void setAccount(String phoneOrEmailOrOther, int... errorCodes) throws
            ApiResponseException {
        boolean isRightFormat = ValidationUtil.isEmail(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[1]);
        }
        if (getRegisteredUserMapper().selectByEmailAddress(phoneOrEmailOrOther) != null) {
            throw new ApiResponseException(errorCodes[3]);
        }
        getRegisteredUser().setEmailAddress(phoneOrEmailOrOther);
    }

    @Override
    public RegisteredUser create(int... errorCodes) throws ApiResponseException {
        if (getRegisteredUser().getEmailAddress() == null || getRegisteredUser().getPassword() == null) {
            throw new ApiResponseException(errorCodes[0]);
        }
        getRegisteredUser().setAccount("email_" + System.currentTimeMillis());
        getRegisteredUser().setRegistrationTime(LocalDateTime.now());
        getRegisteredUser().setAccountStatus(1);

        getRegisteredUserMapper().insert(getRegisteredUser());
        return getRegisteredUser();
    }
}
