package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.config.security.AuthTokenConfig;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/23 18:29
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class RegisteredUserUidLoginer extends BaseRegisteredUserLoginer
        implements UserLoginer {

    @Autowired
    public RegisteredUserUidLoginer(
            PasswordEncoder passwordEncoder,
            MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper) {
        super(passwordEncoder, mapperFacade, registeredUserMapper);
    }


    @Override
    public SystemUser login(Object credentials) {
        long uid = (long) credentials;
        RegisteredUser registeredUser = registeredUserMapper.selectById(uid);
        return processSystemUser(registeredUser);
    }
}
