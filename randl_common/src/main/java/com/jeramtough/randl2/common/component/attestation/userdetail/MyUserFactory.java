package com.jeramtough.randl2.common.component.attestation.userdetail;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.jtweb.component.location.bean.JtLocation;
import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020/1/29 23:50
 * by @author JeramTough
 * </pre>
 */
@Component
public class MyUserFactory implements WithLogger {

    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest servletRequest;
    private final LocationGating locationGating;

    @Autowired
    public MyUserFactory(
            PasswordEncoder passwordEncoder,
            HttpServletRequest servletRequest,
            LocationGating locationGating) {
        this.passwordEncoder = passwordEncoder;
        this.servletRequest = servletRequest;
        this.locationGating = locationGating;
    }

    public RandlUser getAdminUser(RegisterRandlUserParams params) {
        RandlUser randlUser = BeanUtil.copyProperties(params, RandlUser.class);
        randlUser.setRegistrationTime(new Date());
        randlUser.setAccountStatus(1);

        randlUser.setSurfaceImageId(1L);

        //来源于管理员添加
        randlUser.setChannel(UserChannel.ADMIN_ADDED.getCode());

        //添加注册Ip
        String ipAddress = IpAddrUtil.getIpAddr(servletRequest);
        randlUser.setRegistrationIp(ipAddress);

        //设置注册地址
        JtLocation jtLocation = locationGating.getLocationByIpAddress(ipAddress);
        Objects.requireNonNull(jtLocation);
        randlUser.setRegistrationAddress(jtLocation.getAddress());

        randlUser.setPassword(passwordEncoder.encode(params.getPassword()));

        getLogger().info("创建用户对象成功！" + JSON.toJSONString(randlUser));
        return randlUser;
    }

}
