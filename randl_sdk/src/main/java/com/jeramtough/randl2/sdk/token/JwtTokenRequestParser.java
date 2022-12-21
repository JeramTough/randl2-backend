package com.jeramtough.randl2.sdk.token;

import com.jeramtough.randl2.sdk.model.constant.OAuth2Constants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/21 21:14
 * by @author WeiBoWen
 * </pre>
 */
public class JwtTokenRequestParser implements TokenRequestParser {

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

        return jwtToken;
    }

    @Override
    public String getAuthorizationHeader(HttpServletRequest request) {
        final String authorizationToken =
                request.getHeader(OAuth2Constants.AUTHORIZATION_HEADER);
        if (authorizationToken != null && "null".equalsIgnoreCase(authorizationToken)) {
            return null;
        }
        return authorizationToken;
    }
}
