package com.jeramtough.authserver.service;

import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 1:04
 * by @author WeiBoWen
 * </pre>
 */
public interface SsoService {

    Map<String,Object> login(LoginByPasswordParams params);

    String logout();

    Map<String,Object> loginByVerificationCode(LoginByVerificationCodeParams params);
}
