package com.jeramtough.randl2.adminapp.action.filter;

import com.jeramtough.jtweb.action.filter.BaseSwaggerFilter;
import com.jeramtough.randl2.common.action.filter.BaseLoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * <pre>
 * Created on 2022/12/23 上午10:17
 * by @author WeiBoWen
 * </pre>
 */
public class AdminLoginFilter extends
        BaseLoginFilter implements BaseSwaggerFilter {

    private static final String LOGIN_URI = "/access/login";
    private static final String REQUEST_METHOD = "POST";


    public AdminLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(LOGIN_URI, REQUEST_METHOD), authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws
            AuthenticationException {
        if (!REQUEST_METHOD.equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        //认证管理器，去找合适的认证提供者对象进行认证
        try {
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        catch (Exception e) {
            errorHandler(response, e);
            return null;
        }
    }


}
