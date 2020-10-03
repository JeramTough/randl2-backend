package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;

/**
 * <pre>
 * Created on 2020/10/3 11:08
 * by @author WeiBoWen
 * </pre>
 */
public interface LoginService {
    SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials);

    String adminLogout();
}
