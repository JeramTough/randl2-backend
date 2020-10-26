package com.jeramtough.randl2.adminapp.component.userdetail;

import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
    private final HttpServletRequest servletRequest;

    @Autowired
    public MyUserFactory(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            HttpServletRequest servletRequest) {
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
        this.servletRequest = servletRequest;
    }

    public RandlUser getAdminUser(RegisterRandlUserParams params) {
        RandlUser randlUser = mapperFacade.map(params, RandlUser.class);
        randlUser.setRegistrationTime(LocalDateTime.now());
        randlUser.setAccountStatus(1);

        randlUser.setSurfaceImageId(1L);

        //来源于管理员添加
        randlUser.setChannel(UserChannel.ADMIN_ADDED.getCode());

        //添加注册Ip
        String ipAddress = IpAddrUtil.getIpAddr(servletRequest);
        randlUser.setRegistrationIp(ipAddress);

        randlUser.setPassword(passwordEncoder.encode(params.getPassword()));
        return randlUser;
    }

}
