package com.jeramtough.randl2.common.component.login;

import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
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
            RandlUserMapper randlUserMapper, RandlRoleMapper randlRoleMapper) {
        super(passwordEncoder, mapperFacade, randlUserMapper, randlRoleMapper);
    }

    @Override
    public SystemUser login(Object credentials) {
        LoginByPasswordCredentials loginByPasswordCredentials = (LoginByPasswordCredentials) credentials;
        RandlUser randlUser =
                randlUserMapper.selectByCredentials(
                        loginByPasswordCredentials.getCredential());
        if (randlUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(loginByPasswordCredentials.getPassword(),
                            randlUser.getPassword());
            if (passwordIsRight) {
                return processSystemUser(randlUser);
            }
        }
        return null;
    }


}
