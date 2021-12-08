package com.jeramtough.randl2.component.login.user;

import cn.hutool.core.bean.BeanUtil;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * Created on 2020/3/23 19:57
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseRegisteredUserLoginer {

    protected final PasswordEncoder passwordEncoder;
    protected final RandlUserMapper randlUserMapper;
    private final RandlRoleMapper randlRoleMapper;

    protected BaseRegisteredUserLoginer(
            PasswordEncoder passwordEncoder,
            RandlUserMapper randlUserMapper,
            RandlRoleMapper randlRoleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.randlUserMapper = randlUserMapper;
        this.randlRoleMapper = randlRoleMapper;
    }

    protected SystemUser processSystemUser(RandlUser randlUser) {
        SystemUser systemUser = BeanUtil.copyProperties(randlUser, SystemUser.class);
        systemUser.setAccount(randlUser.getAccount());

       /* RandRole randRole = randlRoleMapper.selectById(randlUser.getRoleId());
        systemUser.setRandRole(randRole);*/
        return systemUser;
    }

}
