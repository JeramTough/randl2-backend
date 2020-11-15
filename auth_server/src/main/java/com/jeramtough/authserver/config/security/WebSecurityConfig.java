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
package com.jeramtough.authserver.config.security;

import com.jeramtough.randl2.common.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.config.security.BaseWebSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Joe Grandja
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends BaseWebSecurityConfig {


    private static final String[] OPENED_API_URLS = {
            "/oauth2/keys",
            "/sso/login",
            "/sso/logout",
            "/unlogged.html"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加jwt过滤
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //签权构造者对象
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationConfigurer = http
                .authorizeRequests();

        http.formLogin().loginPage("/unlogged.html").permitAll();

        //放行Swagger的资源
        authorizationConfigurer
                .antMatchers(SWAGGER_URLS).permitAll();

        //开放登录接口
        authorizationConfigurer
                .antMatchers(OPENED_API_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

   /* @Bean
    public UserDetailsService users() throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                users.username("user1").password("password").roles("RANDL_USER").build());
        manager.createUser(
                users.username("admin").password("password").roles("USER",
                        "ADMIN").build());
        return manager;
    }*/


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}