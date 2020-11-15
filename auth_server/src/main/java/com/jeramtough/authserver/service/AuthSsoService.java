package com.jeramtough.authserver.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.LoginCredentials;

/**
 * <pre>
 * Created on 2020/11/15 1:04
 * by @author WeiBoWen
 * </pre>
 */
public interface AuthSsoService {

    SystemUserDto login(LoginCredentials params);

    String logout();
}
