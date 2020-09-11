package com.jeramtough.randl2.adminapp.config.security;

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


    @Value("${jwt_signing_key}")
    private String signingKey;


    public static final String ISSUER = "JeramTough";

    @Value("${jwt_token_validity}")
    private long jwtTokenValidity;

    @Bean(name = "tokenStore")
    public TokenStore tokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean(name = "defaultTokenServices")
    public AuthorizationServerTokenServices configAuthorizationServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);

        //令牌增强设置
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Collections.singletonList(jwtAccessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        //令牌有效时间
        defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 3);
        //刷新令牌有效时间
        defaultTokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 2);
        return defaultTokenServices;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public long getJwtTokenValidity() {
        return jwtTokenValidity;
    }
}
