package com.jeramtough.randl2.common.component.attestation.userdetail;

import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

/**
 * <pre>
 *
 * 超级管理员，硬编码写死得一个超级管理员对象
 *
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

    public static final String ROLE_DESCRIPTION = "超级管理员，拥有一切后台管理员的权限";

    private SystemUser systemUser;

    private RandlRole superAdminRole;

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
                    systemUser.setAccount(appSetting.getAdminAccount());
                    systemUser.setPassword(appSetting.getAdminPassword());
                    systemUser.setSurfaceImageId(SURFACE_IMAGE_ID);
                    systemUser.setRoles(Collections.singletonList(getRole()));
                    systemUser.setAccountStatus(1);
                }
            }
        }
        return systemUser;
    }

    public RandlRole getRole() {
        if (superAdminRole == null) {
            synchronized (this) {
                if (superAdminRole == null) {
                    superAdminRole = new RandlRole();
                    superAdminRole.setCreateTime(new Date());
                    superAdminRole.setAlias(ROLE_ALIAS_NAME);
                    superAdminRole.setAppId(appSetting.getDefaultAdminAppId());
                    superAdminRole.setFid(ROLE_ID);
                    superAdminRole.setName(ROLE_NAME);
                    superAdminRole.setDescription(ROLE_DESCRIPTION);
                }
            }
        }
        return superAdminRole;
    }
}
