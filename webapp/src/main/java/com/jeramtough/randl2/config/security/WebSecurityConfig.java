package com.jeramtough.randl2.config.security;

import com.jeramtough.randl2.action.filter.JwtRequestFilter;
import com.jeramtough.randl2.component.userdetail.RegisteredUserRole;
import com.jeramtough.randl2.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.PermissionMapper;
import com.jeramtough.randl2.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/1/25 17:06
 * by @author JeramTough
 * </pre>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] OPENED_ADI_URLS = {
            "/adminUser/login",
            "/registeredUser/verify/**",
            "/registeredUser/register",
            "/registeredUser/reset",
            "/registeredUser/login/**",
            "/verificationCode/**",
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
            "/configuration/security"
    };

    private static final String[] ONLY_SUPER_ADMIN_API_URLS = {
            "/adminUser/add",
            "/adminUser/all",
            "/adminUser/page",
            "/adminUser/byKeyword",
            "/adminUser/remove",
    };

    private final PermissionMapper permissionMapper;
    private final JwtRequestFilter jwtRequestFilter;


    @Autowired
    public WebSecurityConfig(
            PermissionMapper permissionMapper,
            JwtRequestFilter jwtRequestFilter) {
        this.permissionMapper = permissionMapper;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //添加jwt过滤
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //从数据库读取角色权限
        List<PermissionDto> permissionDtoList = permissionMapper.selectListPermissionDto();
        Map<String, List<String>> apiUrlKeyPermissionMap = new HashMap<>();
        for (PermissionDto permissionDto : permissionDtoList) {
            List<String> roleList = apiUrlKeyPermissionMap.get(permissionDto.getApiPath());
            if (roleList == null) {
                roleList = new ArrayList<>();
                //每一种api都有超级管理员的权限.
                roleList.add(SuperAdmin.ROLE_NAME);
            }
            roleList.add(permissionDto.getRoleName());
            apiUrlKeyPermissionMap.put(permissionDto.getApiPath(), roleList);
        }

        //签权构造者对象
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationConfigurer = http
                .authorizeRequests();

        authorizationConfigurer
                //放行Swagger的资源
                .antMatchers(SWAGGER_URLS).permitAll();

        //动态分配权限
        for (Map.Entry<String, List<String>> entry : apiUrlKeyPermissionMap.entrySet()) {
            String[] roles = entry.getValue().toArray(new String[]{});
            authorizationConfigurer.antMatchers(entry.getKey()).hasAnyRole(roles);
        }

        http.formLogin().loginPage("/unlogged.html").permitAll();
        String[] accessOnlySuperAdminApiUrls = new String[]{"/adminUser/add"};

        //只有超级管理员才能访问的权限
        authorizationConfigurer.antMatchers(accessOnlySuperAdminApiUrls).hasAnyRole(
                SuperAdmin.ROLE_NAME);

        //普通注册用户登录以后才能使用的接口
        authorizationConfigurer.antMatchers("/registeredUserLogined/**")
                               .hasRole(RegisteredUserRole.get().getName());

        //开放登录接口
        authorizationConfigurer
                .antMatchers(OPENED_ADI_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    /**
     * 返回自适应的密码编码者
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
