package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.entity.Role;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
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
public class RegisteredUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final MapperFacade mapperFacade;
    private final RegisteredUserMapper registeredUserMapper;

    @Autowired
    public RegisteredUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
        this.registeredUserMapper = registeredUserMapper;
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
                SystemUser systemUser = mapperFacade.map(registeredUser, SystemUser.class);
                systemUser.setUserType(UserType.REGISTERED);
                systemUser.setRole(null);
                return systemUser;
            }
        }
        return null;
    }
}
