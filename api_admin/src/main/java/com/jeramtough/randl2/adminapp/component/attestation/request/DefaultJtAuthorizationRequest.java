package com.jeramtough.randl2.adminapp.component.attestation.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;

/**
 * <pre>
 * Created on 2022/12/23 下午4:15
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultJtAuthorizationRequest extends BaseJtAuthorizationRequest
        implements JtAuthorizationRequest {

    @Override
    public boolean is(String requestUri) {
        return true;
    }

    @Override
    public AuthorizationDecision decide(HttpServletRequest request) {
        return new AuthorizationDecision(false);
    }
}
