package com.jeramtough.ssoserver.component.attestation.token;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/21 21:14
 * by @author WeiBoWen
 * </pre>
 */
public class JwtTokenRequestParser implements TokenRequestParser, WithLogger {

    @Override
    public String parse(HttpServletRequest request) {
        final String requestToken = request.getHeader(OAuth2Constants.AUTHORIZATION_HEADER);
        String jwtToken = null;
        //从参数中取令牌
        if (requestToken == null) {
            jwtToken = request.getParameter(OAuth2Constants.AUTHORIZATION_PARAMETER);
        }
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestToken != null && requestToken.startsWith(OAuth2Constants.BEARER_PREFIX)) {
            jwtToken = requestToken.substring(7);
        }

        if (requestToken != null && requestToken.startsWith(
                OAuth2Constants.BEARER_PREFIX.toLowerCase())) {
            jwtToken = requestToken.substring(7);
        }

        if (requestToken != null && requestToken.startsWith(
                OAuth2Constants.BEARER_PREFIX.toUpperCase())) {
            jwtToken = requestToken.substring(7);
        }

        if (jwtToken == null) {
            getLogger().warn("JWT Token does not begin with Bearer String");
        }

        return jwtToken;
    }

    @Override
    public String getAuthorizationHeader(HttpServletRequest request) {
        final String requestToken = request.getHeader(OAuth2Constants.AUTHORIZATION_HEADER);
        return requestToken;
    }
}
