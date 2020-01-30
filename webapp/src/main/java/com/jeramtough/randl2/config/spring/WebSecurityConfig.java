package com.jeramtough.randl2.config.spring;

import com.jeramtough.randl2.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.dao.mapper.PermissionMapper;
import com.jeramtough.randl2.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private final PermissionMapper permissionMapper;

    @Autowired
    public WebSecurityConfig(
            PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

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
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll();

        //动态分配权限
        for (Map.Entry<String, List<String>> entry : apiUrlKeyPermissionMap.entrySet()) {
            String[] roles = entry.getValue().toArray(new String[]{});
            authorizationConfigurer.antMatchers(entry.getKey()).hasAnyRole(roles);
        }

        http.formLogin().loginPage("/unlogged.html").permitAll();
        String[] openedApiUrls = new String[]{"/adminUser/login"};
        String[] accessOnlySuperadminApiUrls = new String[]{"/adminUser/add"};

        authorizationConfigurer.antMatchers(accessOnlySuperadminApiUrls).hasAnyRole(
                SuperAdmin.ROLE_NAME);

        //开放登录接口
        authorizationConfigurer
                .antMatchers(openedApiUrls).permitAll()
                .anyRequest().authenticated()
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
}
