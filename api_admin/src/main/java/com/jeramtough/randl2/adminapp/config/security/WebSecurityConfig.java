package com.jeramtough.randl2.adminapp.config.security;

import com.jeramtough.randl2.adminapp.action.controller.AdminLoginController;
import com.jeramtough.randl2.adminapp.action.filter.AdminLoginFilter;
import com.jeramtough.randl2.adminapp.component.attestation.am.MyAuthorizationManager;
import com.jeramtough.randl2.adminapp.component.attestation.provider.AdminDaoAuthenticationProvider;
import com.jeramtough.randl2.adminapp.service.LoginService;
import com.jeramtough.randl2.service.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 * Created on 2020/1/25 17:06
 * by @author JeramTough
 * </pre>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] OPENED_API_URLS = {
//            "/access/login",
//            "/access/logout",
            "/registeredUser/verify/**",
            "/registeredUser/register",
            "/registeredUser/reset",
            "/registeredUser/login/**",
            "/verificationCode/**",
//            "/test/**",
    };

    private static final String[] SWAGGER_URLS = {
            "/swagger-resources",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs-ext",
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


    private final PasswordEncoder passwordEncoder;

    private final MyUserDetailsService myUserDetailsService;
    private final LoginService loginService;

    @Autowired
    public WebSecurityConfig(
            PasswordEncoder passwordEncoder, MyUserDetailsService myUserDetailsService,
            LoginService loginService) {
        this.passwordEncoder = passwordEncoder;
        this.myUserDetailsService = myUserDetailsService;
        this.loginService = loginService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //添加添加自定义管理员登录过滤器
        AdminLoginFilter adminLoginFilter = new AdminLoginFilter(authenticationManagerBean());
        http.addFilterAt(adminLoginFilter,
                UsernamePasswordAuthenticationFilter.class);


        http
                .authorizeHttpRequests((requests) -> requests
                        //所有的请求都使用自定义认证方式
                        .anyRequest().access(new MyAuthorizationManager())
                )
                .logout(logoutConfigurer -> {
                    logoutConfigurer.clearAuthentication(true);
                    logoutConfigurer.logoutUrl(AdminLoginController.BASE_URI +
                            AdminLoginController.LOGOUT_URI);
                    logoutConfigurer.logoutSuccessUrl(
                            AdminLoginController.BASE_URI +
                                    AdminLoginController.LOGOUT_SUCCESSFUL_URI);
                })
                .formLogin((form) -> form
                        .loginPage("/unlogged.html")
                        .permitAll()
                )
                .cors()
                .and()
                //设置session策略
                .sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.IF_REQUIRED);
                })
                .csrf().disable();

        return http.build();
    }

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {


        //签权构造者对象
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationConfigurer = http
                .authorizeRequests();

        //取出menuId对应的api，这是个菜单里需要调用到的api接口的描述
        *//*List<MenuApiPermission> menuApiPermissionList = menuApiPermissionMapper.selectList(null);
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
        List<RandlModule> randlModuleList = randlModuleMapper.selectList(null);
        Map<Long, List<RandRole>> menuIdKeyRoleMap = new HashMap<>(randlModuleList.size());
        for (RandlModule randlModule : randlModuleList) {
            List<RandRole> randRoleList = new ArrayList<>();

            //如果这个菜单有设置角色
            List<Long> roleIdList = menuRolePermissionMapper.selectRoleIdsByMenuId(randlModule.getFid());
            if (roleIdList != null && roleIdList.size() > 0) {
                randRoleList = randlRoleMapper.selectBatchIds(roleIdList);
            }

            //每一个菜单都会添加超级管理员的角色
            randRoleList.add(superAdmin.getSystemUser().getRandRole());

            menuIdKeyRoleMap.put(randlModule.getFid(), randRoleList);
        }



        authorizationConfigurer
                //放行Swagger的资源
                .antMatchers(SWAGGER_URLS).permitAll();

        //动态分配其他管理员权限
        for (Long menuId : menuIdKeyRoleMap.keySet()) {
            List<RandRole> randRoleList = menuIdKeyRoleMap.get(menuId);
            List<SystemApi> appApiList = menuIdKeyApiMap.get(menuId);
            //如果这个菜单设置有API接口的话
            if (appApiList != null && appApiList.size() > 0) {
                for (SystemApi systemApi : appApiList) {
                    String[] roles = new String[randRoleList.size()];
                    for (int i = 0; i < randRoleList.size(); i++) {
                        roles[i] = randRoleList.get(i).getName();
                    }
                    authorizationConfigurer.antMatchers(systemApi.getPath()).hasAnyRole(roles);
                }
            }
        }
        //直接分配超级管理员所有系统接口权限
        List<SystemApi> systemApiList = systemApiMapper.selectList(null);
        for (SystemApi systemApi : systemApiList) {
            authorizationConfigurer.antMatchers(systemApi.getPath()).hasRole(SuperAdmin.ROLE_NAME);
        }*//*

        http.formLogin().loginPage("/unlogged.html").permitAll();

        //只有超级管理员才能访问的权限
        authorizationConfigurer.antMatchers(ONLY_SUPER_ADMIN_API_URLS).hasAnyRole(
                SuperAdmin.ROLE_NAME);

        //放行Swagger的资源
        authorizationConfigurer
                .antMatchers(SWAGGER_URLS).permitAll();

        //普通注册用户登录以后并且还要有普通注册用户的角色才能使用的接口
       *//* authorizationConfigurer.antMatchers("/registeredUserLogined/**")
                               .hasRole(RegisteredUserRole.PrimaryRole.get().getName());*//*

        //开放登录接口
        authorizationConfigurer
                .antMatchers(OPENED_ADI_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }*/

    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(
                new ObjectPostProcessor<Object>() {
                    @Override
                    public <O> O postProcess(O object) {
                        return object;
                    }
                });

        //管理员登录校验算法
        AdminDaoAuthenticationProvider adminDaoAuthenticationProvider =
                new AdminDaoAuthenticationProvider(passwordEncoder, myUserDetailsService,
                        loginService);

        //添加三个令牌校验算法
        /*builder.authenticationProvider(clientDaoAuthenticationProvider);
        builder.authenticationProvider(ssoAuthenticationProvider);*/

        builder.authenticationProvider(adminDaoAuthenticationProvider);

        AuthenticationManager authenticationManager = builder.build();
        return authenticationManager;
    }

}
