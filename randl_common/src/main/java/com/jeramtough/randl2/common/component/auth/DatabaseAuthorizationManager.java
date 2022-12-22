package com.jeramtough.randl2.common.component.auth;

import com.jeramtough.jtlog.facade.L;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

/**
 * <pre>
 * Created on 2022/12/22 上午9:44
 * by @author WeiBoWen
 * </pre>
 */
public class DatabaseAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       HttpServletRequest request) {
        L.arrive();
        return new AuthorizationDecision(true);
    }
}
