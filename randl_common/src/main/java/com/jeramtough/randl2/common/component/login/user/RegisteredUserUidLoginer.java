package com.jeramtough.randl2.common.component.login.user;

import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
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
            RandlUserMapper randlUserMapper
            , RandlRoleMapper randlRoleMapper) {
        super(passwordEncoder, mapperFacade, randlUserMapper, randlRoleMapper);
    }


    @Override
    public SystemUser login(Object credentials) {
        long uid = (long) credentials;
        RandlUser randlUser = randlUserMapper.selectById(uid);
        return processSystemUser(randlUser);
    }
}
