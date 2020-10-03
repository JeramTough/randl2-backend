package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.adminapp.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials) {
        BeanValidator.verifyParams(adminUserCredentials);

        return null;
    }

    @Override
    public String adminLogout() {
        return null;
    }
}
