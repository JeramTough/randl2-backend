package com.jeramtough.randl2.userapp.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/14 21:12
 * by @author WeiBoWen
 * </pre>
 */
public interface LoginService {

    Map<String, Object> userLogin(LoginByPasswordParams params);


}
