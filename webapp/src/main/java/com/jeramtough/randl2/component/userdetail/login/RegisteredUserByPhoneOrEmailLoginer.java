package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/22 21:00
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class RegisteredUserByPhoneOrEmailLoginer extends BaseRegisteredUserLoginer
        implements UserLoginer {


    @Autowired
    protected RegisteredUserByPhoneOrEmailLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper) {
        super(passwordEncoder, mapperFacade, registeredUserMapper);
    }

    @Override
    public SystemUser login(Object credentials) {
        String phoneOrEmail = (String) credentials;
        RegisteredUser registeredUser =
                registeredUserMapper.selectByPhoneNumberOrEmailAddress(
                        phoneOrEmail);
        if (registeredUser != null) {
            return processSystemUser(registeredUser);
        }
        return null;
    }


}
