package com.jeramtough.randl2.common.config.security;

import com.jeramtough.randl2.common.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * <pre>
 * Created on 2020/1/25 17:06
 * by @author JeramTough
 * </pre>
 */
@EnableWebSecurity
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {


    public static final String[] SWAGGER_URLS = {
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


}
