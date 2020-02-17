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
public class PhoneRegisteredUserPlant extends BaseRegisteredUserPlant {

    @Autowired
    public PhoneRegisteredUserPlant(HttpSession session,
                                    PasswordEncoder passwordEncoder,
                                    RegisteredUserMapper registeredUserMapper) {
        super(session, passwordEncoder, registeredUserMapper);
    }

    @Override
    public void setAccount(String phoneOrEmailOrOther,int...errorCodes) throws ApiResponseException {
        boolean isRightFormat = ValidationUtil.isPhone(phoneOrEmailOrOther);
        if (!isRightFormat) {
            throw new ApiResponseException(errorCodes[0]);
        }
        if (getRegisteredUserMapper().selectByPhoneNumber(phoneOrEmailOrOther)!=null){
            throw new ApiResponseException(errorCodes[2]);
        }
        getRegisteredUser().setPhoneNumber(phoneOrEmailOrOther);
    }

    @Override
    public RegisteredUser create(int...errorCodes) throws ApiResponseException {
        if (getRegisteredUser().getPhoneNumber()==null||getRegisteredUser().getPassword()==null){
            throw new ApiResponseException(errorCodes[0]);
        }
        getRegisteredUser().setAccount("phone_"+System.currentTimeMillis());
        getRegisteredUser().setRegistrationTime(LocalDateTime.now());
        getRegisteredUser().setAccountStatus(1);

        getRegisteredUserMapper().insert(getRegisteredUser());
        return getRegisteredUser();
    }
}
