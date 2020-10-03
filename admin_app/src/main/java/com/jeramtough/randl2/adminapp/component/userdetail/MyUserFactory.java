package com.jeramtough.randl2.adminapp.component.userdetail;

import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterRandlUserParams;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/1/29 23:50
 * by @author JeramTough
 * </pre>
 */
@Component
public class MyUserFactory {

    private final PasswordEncoder passwordEncoder;
    private final MapperFacade mapperFacade;

    @Autowired
    public MyUserFactory(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
    }

    public RandlUser getAdminUser(RegisterRandlUserParams params) {
        RandlUser randlUser = mapperFacade.map(params, RandlUser.class);
        randlUser.setRegistrationTime(LocalDateTime.now());
        randlUser.setAccountStatus(1);

        randlUser.setSurfaceImageId(1L);

        //来源于管理员添加
        randlUser.setChannel(UserChannel.ADMIN_ADDED.getCode());

        randlUser.setPassword(passwordEncoder.encode(params.getPassword()));
        return randlUser;
    }

}
