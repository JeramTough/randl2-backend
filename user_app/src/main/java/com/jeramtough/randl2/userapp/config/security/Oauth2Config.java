package com.jeramtough.randl2.userapp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * <pre>
 * Created on 2020/3/18 13:23
 * by @author JeramTough
 * </pre>
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {


    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final AuthorizationServerTokenServices authorizationServerTokenServices;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Oauth2Config(
            AuthenticationManager authenticationManager,
            JwtAccessTokenConverter jwtAccessTokenConverter,
            @Qualifier("defaultTokenServices")
                    AuthorizationServerTokenServices authorizationServerTokenServices,
            DataSource dataSource,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * : defines the security constraints on the token endpoint
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        //开放校验令牌接口
        security.checkTokenAccess("permitAll()");
        security.allowFormAuthenticationForClients();
    }


    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        AuthorizationCodeServices authorizationCodeServices =
                new JdbcAuthorizationCodeServices(dataSource);
        return authorizationCodeServices;
    }

    @Bean(name = "jdbcClientDetailsService")
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService service = new JdbcClientDetailsService(dataSource);
        service.setPasswordEncoder(passwordEncoder);
        return service;
    }

    /**
     * a configurer that defines the client details service. Client details can be initialized,
     * or you can just refer to an existing store.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用jdbc的方法注册clients
        clients.withClientDetails(clientDetailsService());
       /*
       //使用内存的方法注册clients
       clients.inMemory().withClient("first-client")
               .secret("{noop}12345678")
               .scopes("somewhere")
               .authorizedGrantTypes(AuthorizationGrantType.AUTHORIZATION_CODE.getValue(),
                       AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(),
                       AuthorizationGrantType.PASSWORD.getValue(),
                       AuthorizationGrantType.IMPLICIT.getValue(),
                       AuthorizationGrantType.REFRESH_TOKEN.getValue())
               .redirectUris("http://baidu.com")
               //是否自动批准授权
               .autoApprove(false);*/
    }

    /**
     * defines the authorization and token endpoints and the token services
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.setClientDetailsService(clientDetailsService());
        endpoints.tokenServices(authorizationServerTokenServices);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.authorizationCodeServices(authorizationCodeServices());
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
}
