package com.jeramtough.randl2.common.component.clientdetail;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <pre>
 * Created on 2020/11/18 9:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class ClientDaoAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ClientDaoAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            ClientDetailsService clientDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= (UsernamePasswordAuthenticationToken) authentication;

        // 这个获取表单输入中返回的用户名;
        String userName = usernamePasswordAuthenticationToken.getName();
        // 这个是表单中输入的密码；
        String password = (String) usernamePasswordAuthenticationToken.getCredentials();

        //TODO 再写密码校验

        MyClientDetails clientDetails = (MyClientDetails) clientDetailsService.loadClientByClientId(
                userName);
        // 这里调用我们的自己写的获取用户的方法
        Long appId = clientDetails.getOauthClientDetails().getAppId();
        if (appId == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "Randl应用");
        }
//

        Collection<? extends GrantedAuthority> authorities = clientDetails.getAuthorities();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                        authentication.getCredentials(), authorities);
        // 构建返回的用户登录成功的token
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
        return (ClientSecretAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
