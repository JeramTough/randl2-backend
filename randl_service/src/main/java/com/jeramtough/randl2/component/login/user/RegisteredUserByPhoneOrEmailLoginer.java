package com.jeramtough.randl2.component.login.user;

import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;

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
public class RegisteredUserByPhoneOrEmailLoginer extends BaseRegisteredUserLoginer
        implements UserLoginer {


    @Autowired
    protected RegisteredUserByPhoneOrEmailLoginer(
            PasswordEncoder passwordEncoder,
            RandlUserMapper randlUserMapper, RandlRoleMapper randlRoleMapper) {
        super(passwordEncoder, randlUserMapper, randlRoleMapper);
    }

    @Override
    public SystemUser login(Object credentials) {
        String phoneOrEmail = (String) credentials;
        RandlUser randlUser =
                randlUserMapper.selectByPhoneNumberOrEmailAddress(
                        phoneOrEmail);
        if (randlUser != null) {
            return processSystemUser(randlUser);
        }
        return null;
    }


}
