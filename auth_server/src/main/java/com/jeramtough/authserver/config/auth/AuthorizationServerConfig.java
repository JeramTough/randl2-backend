package com.jeramtough.authserver.config.auth;

import com.jeramtough.jtlog.facade.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

/**
 * <pre>
 *
 *     授权服务适配的配置类
 *
 * Created on 2020/3/18 13:23
 * by @author JeramTough
 * </pre>
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final AuthorizationServerTokenServices tokenServices;
    private final DataSource dataSource;
    private final ClientDetailsService clientDetailsService;

    private final WebApplicationContext webApplicationContext;

    @Autowired
    public AuthorizationServerConfig(
            AuthenticationManager authenticationManager,
            JwtAccessTokenConverter jwtAccessTokenConverter,
            @Qualifier("tokenServices")
                    AuthorizationServerTokenServices tokenServices,
            DataSource dataSource,
            @Qualifier("myClientDetailsService")
                    ClientDetailsService clientDetailsService,
            WebApplicationContext webApplicationContext) {
        this.authenticationManager = authenticationManager;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.tokenServices = tokenServices;
        this.dataSource = dataSource;
        this.clientDetailsService = clientDetailsService;
        this.webApplicationContext = webApplicationContext;
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


    /**
     * 生成短时效的授权码
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        AuthorizationCodeServices authorizationCodeServices =
                new JdbcAuthorizationCodeServices(dataSource);
        return authorizationCodeServices;
    }


    /**
     * a configurer that defines the client details service. Client details can be initialized,
     * or you can just refer to an existing store.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用jdbc的方法注册clients
        clients.withClientDetails(clientDetailsService);

        //使用内存的方法注册clients
        /*clients.inMemory().withClient("authorization-code-client")
               .secret("{noop}12345678")
               .scopes("user-resource")
               .authorizedGrantTypes(AuthorizationGrantType.AUTHORIZATION_CODE.getValue(),
                       AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(),
                       AuthorizationGrantType.PASSWORD.getValue(),
                       AuthorizationGrantType.IMPLICIT.getValue(),
                       AuthorizationGrantType.REFRESH_TOKEN.getValue())
               .redirectUris("http://127.0.0.1:9070/randl2/client/authorized")
               //是否自动批准授权
               .autoApprove(false);*/
    }

    /**
     * defines the authorization and token endpoints and the token services
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.setClientDetailsService(clientDetailsService);
        endpoints.tokenServices(tokenServices);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.authorizationCodeServices(authorizationCodeServices());
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

        endpoints.pathMapping("/oauth/confirm_access","/oauthV2/confirmAccess");
//        endpoints.pathMapping("/oauth/error","d.html");

       /* endpoints.exceptionTranslator(new WebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                L.arrive();
                return null;
            }
        });*/

    }



}
