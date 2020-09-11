package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserType;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
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
    protected final RegisteredUserMapper registeredUserMapper;
    private final RoleMapper roleMapper;

    protected BaseRegisteredUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper,
            RoleMapper roleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
        this.registeredUserMapper = registeredUserMapper;
        this.roleMapper = roleMapper;
    }

    protected SystemUser processSystemUser(RegisteredUser registeredUser) {
        SystemUser systemUser = mapperFacade.map(registeredUser, SystemUser.class);
        systemUser.setUserType(UserType.REGISTERED);
        systemUser.setUsername(registeredUser.getAccount());

        //所有用户只能拥有一种角色
        Role role = roleMapper.selectById(registeredUser.getRoleId());
        systemUser.setRole(role);
        return systemUser;
    }

}
