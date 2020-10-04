package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.adminuser.UserCredentials;

/**
 * <pre>
 * Created on 2020/10/3 11:08
 * by @author WeiBoWen
 * </pre>
 */
public interface LoginService {

    SystemUserDto adminLogin(UserCredentials userCredentials);

    String adminLogout();
}
