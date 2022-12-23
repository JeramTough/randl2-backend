package com.jeramtough.randl2.adminapp.component.attestation.am;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

/**
 * <pre>
 * Created on 2022/12/23 下午1:27
 * by @author WeiBoWen
 * </pre>
 */
public class MyAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext authorizationContext) {
        HttpServletRequest request = authorizationContext.getRequest();



        return new AuthorizationDecision(true);
    }
}
