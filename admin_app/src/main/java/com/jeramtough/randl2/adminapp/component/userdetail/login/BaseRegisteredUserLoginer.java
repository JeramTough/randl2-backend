package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserType;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * Created on 2020/3/23 19:57
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseRegisteredUserLoginer {

    protected final PasswordEncoder passwordEncoder;
    protected final MapperFacade mapperFacade;
    protected final RandlUserMapper randlUserMapper;
    private final RandlRoleMapper randlRoleMapper;

    protected BaseRegisteredUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RandlUserMapper randlUserMapper,
            RandlRoleMapper randlRoleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
        this.randlUserMapper = randlUserMapper;
        this.randlRoleMapper = randlRoleMapper;
    }

    protected SystemUser processSystemUser(RandlUser randlUser) {
        SystemUser systemUser = mapperFacade.map(randlUser, SystemUser.class);
        systemUser.setUserType(UserType.REGISTERED);
        systemUser.setUsername(randlUser.getAccount());

       /* RandRole randRole = randlRoleMapper.selectById(randlUser.getRoleId());
        systemUser.setRandRole(randRole);*/
        return systemUser;
    }

}
