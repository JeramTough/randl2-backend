package com.jeramtough.randl2.adminapp.component.attestation.provider;

import com.jeramtough.randl2.adminapp.service.LoginService;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.service.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/18 9:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class AdminDaoAuthenticationProvider extends MyBaseController
        implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService userDetailsService;
    private final LoginService loginService;

    @Autowired
    public AdminDaoAuthenticationProvider(
            PasswordEncoder passwordEncoder, MyUserDetailsService userDetailsService,
            LoginService loginService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;

        this.loginService = loginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
            AuthenticationException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        String username = (String) usernamePasswordAuthenticationToken.getPrincipal();
        String password = (String) usernamePasswordAuthenticationToken.getCredentials();


        loginService.adminLogin(username, password);

        // 返回的用户登录成功的token
        UsernamePasswordAuthenticationToken token = UserHolder.getUsernamePasswordAuthenticationToken();
        return token;
    }

    /**
     * providerManager会遍历所有
     * securityconfig中注册的provider集合
     * 根据此方法返回true或false来决定由哪个provider
     * 去校验请求过来的authentication
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}
