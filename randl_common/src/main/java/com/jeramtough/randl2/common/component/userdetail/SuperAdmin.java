package com.jeramtough.randl2.adminapp.component.userdetail;

import com.jeramtough.randl2.common.model.entity.Role;
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
    private String password;

    public static final String ROLE_NAME = "SUPER_ADMIN";

    private String roleName = ROLE_NAME;

    public static final Long UID = 0L;

    public static final Long SURFACE_IMAGE_ID = 0L;

    public static final Long ROLE_ID = 0L;

    public static final String ROLE_DESCRIPTION = "超级管理员";

    private SystemUser systemUser;


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

    public SystemUser getSystemUser() {
        if (systemUser == null) {
            synchronized (this) {
                if (systemUser == null) {
                    systemUser = new SystemUser();
                    systemUser.setUid(UID);
                    systemUser.setUsername(username);
                    systemUser.setPassword(password);
                    Role role = new Role();
                    role.setDescription(ROLE_DESCRIPTION);
                    role.setFid(ROLE_ID);
                    role.setName(roleName);
                    systemUser.setRole(role);
                    systemUser.setUserType(UserType.ADMIN);
                    systemUser.setSurfaceImageId(SURFACE_IMAGE_ID);
                }
            }
        }
        return systemUser;
    }
}
