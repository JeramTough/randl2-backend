package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtcomponent.utils.ValidationUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/2/16 18:54
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class PhoneRegisteredUserBuilder extends BaseRegisteredUserBuilder {

    @Autowired
    public PhoneRegisteredUserBuilder(HttpSession session,
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
        RegisteredUser registeredUser = getRegisteredUser();
        registeredUser.setPhoneNumber(phoneOrEmailOrOther);
        setRegisteredUser(registeredUser);
    }

    @Override
    public RegisteredUser build(int...errorCodes) throws ApiResponseException {
        if (getRegisteredUser().getPhoneNumber()==null||getRegisteredUser().getPassword()==null){
            throw new ApiResponseException(errorCodes[0]);
        }
        getRegisteredUser().setAccount("phone_"+System.currentTimeMillis());
        getRegisteredUser().setRegistrationTime(LocalDateTime.now());
        getRegisteredUser().setAccountStatus(1);
        getRegisteredUser().setSurfaceImageId(2L);
        setRegisteredUser(getRegisteredUser());
        return getRegisteredUser();
    }
}
