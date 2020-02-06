package com.jeramtough.randl2.component.userdetail.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.dao.entity.AdminUser;
import com.jeramtough.randl2.dao.entity.Role;
import com.jeramtough.randl2.dao.entity.SurfaceImage;
import com.jeramtough.randl2.dao.mapper.AdminUserMapper;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
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
    private final AdminUserMapper adminUserMapper;
    private final RoleMapper roleMapper;
    private final MapperFacade mapperFacade;
    private final SurfaceImageMapper surfaceImageMapper;


    public AdminUserLoginer(
            PasswordEncoder passwordEncoder,
            SuperAdmin superAdmin,
            AdminUserMapper adminUserMapper,
            RoleMapper roleMapper, MapperFacade mapperFacade,
            SurfaceImageMapper surfaceImageMapper) {
        this.passwordEncoder = passwordEncoder;
        this.superAdmin = superAdmin;
        this.adminUserMapper = adminUserMapper;
        this.roleMapper = roleMapper;
        this.mapperFacade = mapperFacade;
        this.surfaceImageMapper = surfaceImageMapper;
    }

    @Override
    public SystemUser login(Object credentials) {
        AdminUserCredentials adminUserCredentials = (AdminUserCredentials) credentials;

        //如果是超级管理员登录
        if (superAdmin.getUsername().equals(
                adminUserCredentials.getUsername()) && passwordEncoder
                .matches(adminUserCredentials.getPassword(),
                        superAdmin.getPassword())) {
            return superAdmin.toSystemUser();
        }

        //如果是普通的系统管理员登录
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminUserCredentials.getUsername());
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        if (adminUser != null) {
            boolean passwordIsRight =
                    passwordEncoder.matches(adminUserCredentials.getPassword(),
                            adminUser.getPassword());
            if (passwordIsRight) {

                //所有用户只能拥有一种角色
                Role role = roleMapper.selectById(adminUser.getRoleId());
                SystemUser systemUser = mapperFacade.map(adminUser, SystemUser.class);
                systemUser.setUserType(UserType.ADMIN);
                systemUser.setRole(role);

                return systemUser;
            }
        }

        return null;
    }
}
