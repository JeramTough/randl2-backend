package com.jeramtough.randl2.resource.action.filter;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.randl2.common.action.filter.BaseCredentialsTokenFilter;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.params.oauth.OauthTokenParams;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *   除了客户端模式，其他授权模式都包含用户信息
 *
 * Created on 2020/11/17 22:32
 * by @author WeiBoWen
 * </pre>
 */
public class UserCredentialsTokenFilter extends BaseCredentialsTokenFilter {


    private final AuthenticationManager authenticationManager;


    public UserCredentialsTokenFilter(
            AuthenticationManager authenticationManager) {
        this("/api/user", authenticationManager);
    }

    public UserCredentialsTokenFilter(String path,
                                      AuthenticationManager authenticationManager) {
        super(path);
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws
            AuthenticationException, IOException, ServletException {
        return null;
    }
}

