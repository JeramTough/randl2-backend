package com.jeramtough.randl2.common.component.userdetail.login;

import com.jeramtough.randl2.common.model.params.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
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
            RegisteredUserMapper registeredUserMapper, RoleMapper roleMapper) {
        super(passwordEncoder, mapperFacade, registeredUserMapper, roleMapper);
    }

    @Override
    public SystemUser login(Object credentials) {
        LoginByPasswordCredentials loginByPasswordCredentials = (LoginByPasswordCredentials) credentials;
        RegisteredUser registeredUser =
                registeredUserMapper.selectByCredentials(
                        loginByPasswordCredentials.getCredential());
        if (registeredUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(loginByPasswordCredentials.getPassword(),
                            registeredUser.getPassword());
            if (passwordIsRight) {
                return processSystemUser(registeredUser);
            }
        }
        return null;
    }


}
