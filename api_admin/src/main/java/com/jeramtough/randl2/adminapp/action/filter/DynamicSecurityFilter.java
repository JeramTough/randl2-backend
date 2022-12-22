package com.jeramtough.randl2.adminapp.action.filter;

import com.jeramtough.jtlog.facade.L;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.io.IOException;

/**
 * <pre>
 * Created on 2022/12/21 下午3:59
 * by @author WeiBoWen
 * </pre>
 */
public class DynamicSecurityFilter extends AuthorizationFilter implements Filter {

    public DynamicSecurityFilter(
            AuthorizationManager<HttpServletRequest> authorizationManager) {
        super(authorizationManager);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        L.debug(httpServletRequest.getRequestURL().toString());

        //然后才进入到SpringSecurity体系的授权认证
        super.doFilter(servletRequest, servletResponse, chain);
    }
}
