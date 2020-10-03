package com.jeramtough.randl2.adminapp.component.userdetail;

import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/1/27 21:22
 * by @author JeramTough
 * </pre>
 */
@Component
public class SuperAdmin {


    public static final String ROLE_ALIAS_NAME = "SUPER_ADMIN";

    private String roleName = ROLE_NAME;

    public static final Long UID = 0L;

    public static final Long SURFACE_IMAGE_ID = 0L;

    public static final Long ROLE_ID = 0L;

    public static final String ROLE_NAME = "超级管理员";

    private SystemUser systemUser;

    private final AppSetting appSetting;

    @Autowired
    public SuperAdmin(AppSetting appSetting) {
        this.appSetting = appSetting;
    }


    public String getUsername() {
        return appSetting.getAdminAccount();
    }


    public String getPassword() {
        return appSetting.getAdminPassword();
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
                    systemUser.setUsername(appSetting.getAdminAccount());
                    systemUser.setPassword(appSetting.getAdminPassword());
                    systemUser.setUserType(UserType.ADMIN);
                    systemUser.setSurfaceImageId(SURFACE_IMAGE_ID);
                    systemUser.setRoleId(ROLE_ID);
                    systemUser.setRoleName(ROLE_NAME);
                    systemUser.setRoleAliasName(ROLE_ALIAS_NAME);
                    systemUser.setAccountStatus(1);
                }
            }
        }
        return systemUser;
    }
}
