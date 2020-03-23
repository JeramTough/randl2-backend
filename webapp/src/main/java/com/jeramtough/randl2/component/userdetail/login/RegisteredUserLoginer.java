package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials;
import com.jeramtough.randl2.component.userdetail.RegisteredUserRole;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserType;
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
public class RegisteredUserLoginer extends BaseRegisteredUserLoginer implements UserLoginer {


    @Autowired
    protected RegisteredUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper) {
        super(passwordEncoder, mapperFacade, registeredUserMapper);
    }

    @Override
    public SystemUser login(Object credentials) {
        RegisteredUserCredentials registeredUserCredentials = (RegisteredUserCredentials) credentials;
        RegisteredUser registeredUser =
                registeredUserMapper.selectByCredentials(
                        registeredUserCredentials.getCredential());
        if (registeredUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(registeredUserCredentials.getPassword(),
                            registeredUser.getPassword());
            if (passwordIsRight) {
                return processSystemUser(registeredUser);
            }
        }
        return null;
    }


}
