package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserType;
import com.jeramtough.randl2.common.model.entity.RandRole;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/1/30 10:31
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class AdminUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final SuperAdmin superAdmin;
    private final RandlRoleMapper randlRoleMapper;
    private final MapperFacade mapperFacade;


    public AdminUserLoginer(
            PasswordEncoder passwordEncoder,
            SuperAdmin superAdmin,
            RandlRoleMapper randlRoleMapper, MapperFacade mapperFacade) {
        this.passwordEncoder = passwordEncoder;
        this.superAdmin = superAdmin;
        this.randlRoleMapper = randlRoleMapper;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public SystemUser login(Object credentials) {
        /*AdminUserCredentials adminUserCredentials = (AdminUserCredentials) credentials;

        //如果是超级管理员登录
        if (superAdmin.getUsername().equals(
                adminUserCredentials.getUsername()) && passwordEncoder
                .matches(adminUserCredentials.getPassword(),
                        superAdmin.getPassword())) {
            return superAdmin.getSystemUser();
        }

        //如果是普通的系统管理员登录
        QueryWrapper<RandlAdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminUserCredentials.getUsername());
        RandlAdminUser randlAdminUser = adminUserMapper.selectOne(queryWrapper);
        if (randlAdminUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(adminUserCredentials.getPassword(),
                            randlAdminUser.getPassword());
            if (passwordIsRight) {

                //所有用户只能拥有一种角色
                RandRole randRole = randlRoleMapper.selectById(randlAdminUser.getRoleId());
                SystemUser systemUser = mapperFacade.map(randlAdminUser, SystemUser.class);
                systemUser.setUserType(UserType.ADMIN);
                systemUser.setRandRole(randRole);

                return systemUser;
            }
        }*/

        return null;
    }
}
