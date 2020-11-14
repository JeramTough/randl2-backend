package com.jeramtough.randl2.service.registeruser;

import com.jeramtough.randl2.common.model.params.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.common.model.params.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.common.model.params.registereduser.LoginForVisitorCredentials;

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
