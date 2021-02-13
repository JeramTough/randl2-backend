package com.jeramtough.ssoserver.service;

import com.jeramtough.randl2.common.model.params.login.SsoLoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByVerificationCodeParams;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 1:04
 * by @author WeiBoWen
 * </pre>
 */
public interface SsoLoginService {

    Map<String, Object> loginByPassword(SsoLoginByPasswordParams params);

    String logout();

    Map<String, Object> loginByVerificationCode(SsoLoginByVerificationCodeParams params);
}
