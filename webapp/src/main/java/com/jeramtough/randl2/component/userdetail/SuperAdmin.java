package com.jeramtough.randl2.component.userdetail;

import com.jeramtough.randl2.dao.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/1/27 21:22
 * by @author JeramTough
 * </pre>
 */
@Component
public class SuperAdmin {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private  String password;

    public static final String ROLE_NAME="SUPER_ADMIN";
    public static final String ROLE="ROLE_SUPER_ADMIN";

    private String roleName = ROLE_NAME;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public SystemUser toSystemUser(){
        SystemUser systemUser =new SystemUser();
        systemUser.setUid(0L);
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        Role role=new Role();
        role.setDescription("超级管理员");
        role.setFid(0);
        role.setName(roleName);
        systemUser.setRole(role);
        systemUser.setUserType(UserType.ADMIN);
        return systemUser;
    }
}
