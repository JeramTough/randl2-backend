package com.jeramtough.authserver.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collections;

/**
 * <pre>
 * Created on 2020/11/15 0:33
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
public class AuthTokenServicesConfig {

    @Value(value = "${app.setting.jwt.signingKey}")
    private String jwtSigningKey;

    @Value(value = "${app.setting.jwt.validity}")
    private Long jwtValidity;

    @Value(value = "${app.setting.jwt.issuer}")
    private String jwtIssuer;

    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    public AuthTokenServicesConfig(TokenStore tokenStore,
                                   JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.tokenStore = tokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Bean(name = "defaultTokenServices")
    public AuthorizationServerTokenServices configAuthorizationServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);

        //令牌增强设置
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Collections.singletonList(jwtAccessTokenConverter));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        //令牌有效时间
        defaultTokenServices.setAccessTokenValiditySeconds(Math.toIntExact(jwtValidity));
        //刷新令牌有效时间
        defaultTokenServices.setRefreshTokenValiditySeconds(Math.toIntExact(jwtValidity) * 2);
        return defaultTokenServices;
    }

}
