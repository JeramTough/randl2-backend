package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.bean.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.bean.registereduser.LoginForVisitorCredentials;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/4/25 22:46
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserLoginService {

    /**
     * 登录根据之前已经存在的token
     */
    void loginByExistingToken(String token);

    Map<String, Object> loginByPassword(LoginByPasswordCredentials credentials);

    Map<String, Object> loginByVerificationCode(
            LoginByVerificationCodeCredentials credentials);

    Map<String, Object> loginForVisitor(LoginForVisitorCredentials credentials);
}
