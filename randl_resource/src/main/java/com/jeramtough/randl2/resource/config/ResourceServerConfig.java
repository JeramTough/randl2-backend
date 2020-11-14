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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Joe Grandja
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "messages-resource";

    private final TokenStore tokenStore;

    @Autowired
    public ResourceServerConfig(
            @Qualifier("tokenStore") TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer security) throws Exception {
        security
                .resourceId(RESOURCE_ID)
//                不使用远程校验令牌的方法
//                .tokenServices(tokenService())
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //所有资源都要授权访问
        http.antMatcher("/messages/**")
            .authorizeRequests()
            .antMatchers("/messages/two").hasRole("ADMIN")
            //这个资源需要拥有的范围访问资源
            .antMatchers("/messages/one")
            .access("#oauth2.hasScope('somewhere')")
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