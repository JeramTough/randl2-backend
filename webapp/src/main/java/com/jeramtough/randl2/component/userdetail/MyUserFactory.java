package com.jeramtough.randl2.component.userdetail;

import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.model.entity.AdminUser;
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

    public AdminUser getAdminUser(RegisterAdminUserParams params) {
        AdminUser adminUser = mapperFacade.map(params, AdminUser.class);
        adminUser.setRegistrationTime(LocalDateTime.now());
        adminUser.setAccountStatus(1);
        params.setRoleId(params.getRoleId() == null ? 1L : params.getRoleId());
        adminUser.setRoleId(params.getRoleId());
        adminUser.setSurfaceImageId(1L);
        adminUser.setPassword(passwordEncoder.encode(params.getPassword()));
        return adminUser;
    }

}
