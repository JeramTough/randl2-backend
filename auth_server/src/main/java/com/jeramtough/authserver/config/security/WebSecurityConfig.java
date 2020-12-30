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

import com.jeramtough.authserver.action.filter.Oauth2ClientCredentialsTokenFilter;
import com.jeramtough.authserver.action.filter.SsoCredentialsTokenFilter;
import com.jeramtough.authserver.component.attestation.provider.ClientDaoAuthenticationProvider;
import com.jeramtough.authserver.component.attestation.provider.SsoAuthenticationProvider;
import com.jeramtough.randl2.common.config.security.BaseWebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author Joe Grandja
 */
@Configuration
@EnableWebSecurity
@Order(999)
public class WebSecurityConfig extends BaseWebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ClientDaoAuthenticationProvider clientDaoAuthenticationProvider;
    private final SsoAuthenticationProvider ssoAuthenticationProvider;

    private static final String[] OPENED_API_URLS = {
            "/",
            "/oauth2/keys",
            "/sso/**",
            "/test/testLogined2",
//            "/oauth3/token",
            "/unlogged.html",
            "/abc.html",
            "/d.html",
            "/oauthV2/confirmAccess",
            "/error",
    };

    @Autowired
    public WebSecurityConfig(
            @Qualifier("myUserDetailsServiceImpl")
                    UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            ClientDaoAuthenticationProvider clientDaoAuthenticationProvider,
            SsoAuthenticationProvider ssoAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.clientDaoAuthenticationProvider = clientDaoAuthenticationProvider;
        this.ssoAuthenticationProvider = ssoAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加ClientCredentialsToken过滤
        Oauth2ClientCredentialsTokenFilter oauth2ClientCredentialsTokenFilter = new Oauth2ClientCredentialsTokenFilter(
                authenticationManagerBean());
        SsoCredentialsTokenFilter ssoCredentialsTokenFilter =
                new SsoCredentialsTokenFilter(authenticationManagerBean());
        http.addFilterBefore(oauth2ClientCredentialsTokenFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(ssoCredentialsTokenFilter, Oauth2ClientCredentialsTokenFilter.class);
       /* http.addFilterBefore(oauth2ClientCredentialsTokenFilter,
                SessionManagementFilter.class);*/

        /*http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                L.arrive();
            }
        });

        http.exceptionHandling().defaultAuthenticationEntryPointFor(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                L.arrive();
            }
        }, new AntPathRequestMatcher("/oauth/**"));*/

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
                //基于token的话，session就不用缓存了
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(clientDaoAuthenticationProvider);
        auth.authenticationProvider(daoAuthenticationProvider);
        super.configure(auth);
    }*/




    /*@Bean("authenticationManager")
    @Override
    public AuthenticationManager getAuthenticationManagerBean() throws Exception {
        return super.getAuthenticationManagerBean();
    }*/

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(new ObjectPostProcessor<Object>() {
            @Override
            public <O> O postProcess(O object) {
                return object;
            }
        });
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        //添加三个令牌校验算法
        builder.authenticationProvider(clientDaoAuthenticationProvider);
        builder.authenticationProvider(ssoAuthenticationProvider);
        builder.authenticationProvider(daoAuthenticationProvider);

        AuthenticationManager authenticationManager = builder.build();
        return authenticationManager;
    }
}