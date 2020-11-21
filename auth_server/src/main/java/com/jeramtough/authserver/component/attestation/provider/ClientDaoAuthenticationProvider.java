package com.jeramtough.authserver.component.attestation.provider;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.authserver.component.attestation.token.ClientSecretAuthenticationToken;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        ClientSecretAuthenticationToken clientSecretAuthenticationToken = (ClientSecretAuthenticationToken) authentication;

        // 这个获取表单输入中返回的用户名;
        String clientId = clientSecretAuthenticationToken.getClientId();
        // 这个是表单中输入的密码；
        String clientSecret = clientSecretAuthenticationToken.getClientSecret();

        //TODO 再写密码校验

        MyClientDetails clientDetails = (MyClientDetails) clientDetailsService.loadClientByClientId(
                clientId);
        // 这里调用我们的自己写的获取用户的方法
        Long appId = clientDetails.getOauthClientDetails().getAppId();
        if (appId == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "Randl应用");
        }

        List<GrantedAuthority> authorities = new ArrayList<>(clientDetails.getAuthorities());

        for (String scope : clientDetails.getScope()) {
            authorities.add(new SimpleGrantedAuthority(scope));
        }
        for (String resource : clientDetails.getResourceIds()) {
            authorities.add(new SimpleGrantedAuthority(resource));
        }

        // 构建返回的用户登录成功的token
        ClientSecretAuthenticationToken token =
                new ClientSecretAuthenticationToken(clientId, clientSecret, authorities);

        token.setDetails(clientDetails);

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
