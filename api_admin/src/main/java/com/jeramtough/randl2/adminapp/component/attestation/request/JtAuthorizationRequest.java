package com.jeramtough.randl2.adminapp.component.attestation.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;

/**
 * <pre>
 * Created on 2022/12/23 下午3:55
 * by @author WeiBoWen
 * </pre>
 */
public interface JtAuthorizationRequest {

    boolean is(String requestUri);

    AuthorizationDecision decide(HttpServletRequest request);

}
