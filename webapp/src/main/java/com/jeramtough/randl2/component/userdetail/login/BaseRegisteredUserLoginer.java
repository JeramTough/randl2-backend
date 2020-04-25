package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.component.userdetail.RegisteredUserRole;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * Created on 2020/3/23 19:57
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseRegisteredUserLoginer {

    final PasswordEncoder passwordEncoder;
    final MapperFacade mapperFacade;
    final RegisteredUserMapper registeredUserMapper;

    protected BaseRegisteredUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
        this.registeredUserMapper = registeredUserMapper;
    }

    protected SystemUser processSystemUser(RegisteredUser registeredUser) {
        SystemUser systemUser = mapperFacade.map(registeredUser, SystemUser.class);
        systemUser.setUserType(UserType.REGISTERED);
        systemUser.setRole(RegisteredUserRole.get());
        systemUser.setUsername(registeredUser.getAccount());
        return systemUser;
    }

}
