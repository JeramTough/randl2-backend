package com.jeramtough.ssoserver.component.attestation.provider;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.ssoserver.component.attestation.token.ClientSecretAuthenticationToken;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ClientDaoAuthenticationProvider extends MyBaseController implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ClientDaoAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            @Qualifier("oauthClientDetailsServiceImpl")
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

        MyClientDetails clientDetails = (MyClientDetails) clientDetailsService.loadClientByClientId(
                clientId);
        String rightClientSecret = clientDetails.getClientSecret();

        //增加解谜头
        rightClientSecret="{noop}"+rightClientSecret;

        //密码Secret校验
        if (!passwordEncoder.matches(clientSecret, rightClientSecret)) {
            throw new ApiResponseException(ErrorU.CODE_804.C);
        }

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
