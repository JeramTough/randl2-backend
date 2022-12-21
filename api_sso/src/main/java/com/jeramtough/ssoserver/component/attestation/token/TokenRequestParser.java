package com.jeramtough.ssoserver.component.attestation.token;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/21 21:13
 * by @author WeiBoWen
 * </pre>
 */
public interface TokenRequestParser {

    String parse(HttpServletRequest request);

    String getAuthorizationHeader(HttpServletRequest request);

}
