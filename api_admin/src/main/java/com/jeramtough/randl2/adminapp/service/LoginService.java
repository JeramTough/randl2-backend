package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;

/**
 * <pre>
 * Created on 2020/11/14 21:12
 * by @author WeiBoWen
 * </pre>
 */
public interface LoginService  {

    SystemUserDto adminLogin(UserCredentials userCredentials);

    String adminLogout();


}
