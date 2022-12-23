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

    void adminLogin(String username,String password);
    void adminLogin(UserCredentials userCredentials);


    /**
     * 到了这里，其实已经在过滤器登录成功了
     */
    SystemUserDto adminLoginSuccessful();

    String adminLogoutSuccessful();

    String unlogged();

    String denied();
}
