package com.jeramtough.authserver.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.LoginCredentialsParams;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 1:04
 * by @author WeiBoWen
 * </pre>
 */
public interface SsoService {

    Map<String,Object> login(LoginCredentialsParams params);

    String logout();
}
