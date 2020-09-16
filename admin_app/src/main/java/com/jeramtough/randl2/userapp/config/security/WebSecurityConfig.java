package com.jeramtough.randl2.userapp.config.security;

import com.jeramtough.randl2.common.component.userdetail.RegisteredUserRole;
import com.jeramtough.randl2.common.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.mapper.*;
import com.jeramtough.randl2.common.model.entity.MenuApiPermission;
import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.common.model.entity.SystemApi;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
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
            "/test/**",
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

    private static final String[] ONLY_SUPER_ADMIN_API_URLS = {
            "/adminUser/add",
            "/adminUser/all",
            "/adminUser/page",
            "/adminUser/byKeyword",
            "/adminUser/remove",
    };

    private final SuperAdmin superAdmin;
    private final SystemMenuMapper systemMenuMapper;
    private final MenuApiPermissionMapper menuApiPermissionMapper;
    private final MenuRolePermissionMapper menuRolePermissionMapper;
    private final SystemApiMapper systemApiMapper;
    private final RoleMapper roleMapper;


    @Autowired
    public WebSecurityConfig(
            SuperAdmin superAdmin,
            SystemMenuMapper systemMenuMapper,
            MenuApiPermissionMapper menuApiPermissionMapper,
            MenuRolePermissionMapper menuRolePermissionMapper,
            SystemApiMapper systemApiMapper,
            RoleMapper roleMapper) {
        this.superAdmin = superAdmin;
        this.systemMenuMapper = systemMenuMapper;
        this.menuApiPermissionMapper = menuApiPermissionMapper;
        this.menuRolePermissionMapper = menuRolePermissionMapper;
        this.systemApiMapper = systemApiMapper;
        this.roleMapper = roleMapper;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //添加jwt过滤
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //取出menuId对应的api，这是个菜单里需要调用到的api接口的描述
        List<MenuApiPermission> menuApiPermissionList = menuApiPermissionMapper.selectList(null);
        Map<Long, List<SystemApi>> menuIdKeyApiMap = new HashMap<>(24);
        for (MenuApiPermission menuApiPermission : menuApiPermissionList) {
            List<SystemApi> appApiList = menuIdKeyApiMap.get(menuApiPermission.getMenuId());
            if (appApiList == null) {
                appApiList = new ArrayList<>();
            }
            appApiList.add(systemApiMapper.selectById(menuApiPermission.getApiId()));
            menuIdKeyApiMap.put(menuApiPermission.getMenuId(), appApiList);
        }

        //取出menuId对应的角色
        List<SystemMenu> systemMenuList = systemMenuMapper.selectList(null);
        Map<Long, List<Role>> menuIdKeyRoleMap = new HashMap<>(systemMenuList.size());
        for (SystemMenu systemMenu : systemMenuList) {
            List<Role> roleList = new ArrayList<>();

            //如果这个菜单有设置角色
            List<Long> roleIdList = menuRolePermissionMapper.selectRoleIdsByMenuId(systemMenu.getFid());
            if (roleIdList != null && roleIdList.size() > 0) {
                roleList = roleMapper.selectBatchIds(roleIdList);
            }

            //每一个菜单都会添加超级管理员的角色
            roleList.add(superAdmin.getSystemUser().getRole());

            menuIdKeyRoleMap.put(systemMenu.getFid(), roleList);
        }

        //签权构造者对象
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationConfigurer = http
                .authorizeRequests();

        authorizationConfigurer
                //放行Swagger的资源
                .antMatchers(SWAGGER_URLS).permitAll();

        //动态分配权限
        for (Long menuId : menuIdKeyRoleMap.keySet()) {
            List<Role> roleList = menuIdKeyRoleMap.get(menuId);
            List<SystemApi> appApiList = menuIdKeyApiMap.get(menuId);
            //如果这个菜单设置有API接口的话
            if (appApiList != null && appApiList.size() > 0) {
                for (SystemApi systemApi : appApiList) {
                    String[] roles = new String[roleList.size()];
                    for (int i = 0; i < roleList.size(); i++) {
                        roles[i] = roleList.get(i).getName();
                    }
                    authorizationConfigurer.antMatchers(systemApi.getPath()).hasAnyRole(roles);
                }
            }
        }

        http.formLogin().loginPage("/unlogged.html").permitAll();

        //只有超级管理员才能访问的权限
        authorizationConfigurer.antMatchers(ONLY_SUPER_ADMIN_API_URLS).hasAnyRole(
                SuperAdmin.ROLE_NAME);

        //普通注册用户登录以后并且还要有普通注册用户的角色才能使用的接口
        authorizationConfigurer.antMatchers("/registeredUserLogined/**")
                               .hasRole(RegisteredUserRole.PrimaryRole.get().getName());

        //开放登录接口
        authorizationConfigurer
                .antMatchers(OPENED_ADI_URLS).permitAll()
                .anyRequest()
                .authenticated()
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
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
