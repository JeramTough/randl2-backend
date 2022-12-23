package com.jeramtough.randl2.adminapp.component.attestation.am;

import com.jeramtough.randl2.adminapp.component.attestation.request.JtAuthorizationRequest;
import com.jeramtough.randl2.adminapp.component.attestation.request.RequestSelector;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
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

    private final ApplicationContext applicationContext;

    public MyAuthorizationManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext authorizationContext) {
        HttpServletRequest request = authorizationContext.getRequest();

        RequestSelector requestSelector = applicationContext.getBean(RequestSelector.class);

        JtAuthorizationRequest jtAuthorizationRequest = requestSelector.select(request);

        return jtAuthorizationRequest.decide(request);
    }
}
