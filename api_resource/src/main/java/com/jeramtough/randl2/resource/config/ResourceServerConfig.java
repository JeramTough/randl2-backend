/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeramtough.randl2.resource.config;

import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.List;
import java.util.Objects;

/**
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] OPENED_ADI_URLS = {
            "/api/verificationCode/**",
    };

    private static final String[] SWAGGER_URLS = {
            "/swagger-resources",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/doc.html",
            "/webjars",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/images/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/api-docs-ext",
            "/api-docs",
            "/swagger-resources/configuration/ui/**",
            "/swagger-resources/configuration/security"
    };

    private final TokenStore tokenStore;
    private final AppSetting appSetting;

    private final OauthResourceDetailsService oauthResourceDetailsService;
    private final OauthScopeDetailsService oauthScopeDetailsService;

    @Autowired
    public ResourceServerConfig(
            @Qualifier("tokenStore") TokenStore tokenStore,
            AppSetting appSetting,
            OauthResourceDetailsService oauthResourceDetailsService,
            OauthScopeDetailsService oauthScopeDetailsService) {
        this.tokenStore = tokenStore;
        this.appSetting = appSetting;
        this.oauthResourceDetailsService = oauthResourceDetailsService;
        this.oauthScopeDetailsService = oauthScopeDetailsService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer security) throws Exception {
        security
                .resourceId(appSetting.getOauthResourceId().toString())
//                不使用远程校验令牌的方法
//                .tokenServices(tokenService())
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        List<OauthScopeDetailsDto> oauthScopeDetailsDtoList =
                oauthScopeDetailsService.getClientScopeListByResourceId(
                        appSetting.getOauthResourceId());

        //所有资源都要授权访问
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry =
                http.antMatcher("/**")
                    .authorizeRequests();

        //放行Swagger的资源
        expressionInterceptUrlRegistry
                .antMatchers(SWAGGER_URLS).permitAll()
                //放行开放的资源
                .antMatchers(OPENED_ADI_URLS).permitAll();

        //从数据库中提取配置信息
        oauthScopeDetailsDtoList
                .parallelStream()
                .forEach(oauthScopeDetailsDto -> {
                    //授权资源
                    String access = String.format("#oauth2.hasScope('%s')",
                            oauthScopeDetailsDto.getScopeExpression());
                    expressionInterceptUrlRegistry
                            .antMatchers(oauthScopeDetailsDto.getScopeExpression())
                            .access(access);

                });

        expressionInterceptUrlRegistry
                .and()
                //基于token的话，session就不用缓存了
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    /**
     * 配置远程令牌校验服务
     */
    /*@Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(
                "http://127.0.0.1:8080/oauth/check_token");
        remoteTokenServices.setClientId("first-client");
        remoteTokenServices.setClientSecret("12345678");
        return remoteTokenServices;
    }*/
}