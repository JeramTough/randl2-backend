package com.jeramtough.randl2.common.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collections;

/**
 * <pre>
 * Created on 2020/3/19 17:20
 * by @author JeramTough
 * </pre>
 */
@Configuration
public class AuthTokenConfig {

    @Value(value = "${app.setting.jwt.signingKey}")
    private String jwtSigningKey;

    @Value(value = "${app.setting.jwt.validity}")
    private Long jwtValidity;

    @Value(value = "${app.setting.jwt.issuer}")
    private String jwtIssuer;

    @Bean(name = "tokenStore")
    public TokenStore tokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSigningKey);
        return converter;
    }


    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public long getJwtTokenValidity() {
        return jwtValidity;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }
}
