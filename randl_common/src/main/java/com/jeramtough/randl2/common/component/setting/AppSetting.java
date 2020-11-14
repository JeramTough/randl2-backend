package com.jeramtough.randl2.common.component.setting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/9/28 14:38
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class AppSetting {

    @Value(value = "${app.setting.jwt.signingKey}")
    private String jwtSigningKey;

    @Value(value = "${app.setting.jwt.validity}")
    private Long jwtValidity;

    @Value(value = "${app.setting.jwt.issuer}")
    private String jwtIssuer;

    @Value(value = "${app.setting.admin.defaultAppId}")
    private Long defaultAdminAppId;

    @Value(value = "${app.setting.admin.defaultRoleId}")
    private Long defaultAdminRoleId;

    @Value(value = "${app.setting.user.defaultAppId}")
    private Long defaultUserAppId;

    @Value(value = "${app.setting.user.defaultRoleId}")
    private Long defaultUserRoleId;

    @Value(value = "${app.setting.admin.account}")
    private String adminAccount;

    @Value(value = "${app.setting.admin.password}")
    private String adminPassword;

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public Long getJwtValidity() {
        return jwtValidity;
    }

    public void setJwtValidity(Long jwtValidity) {
        this.jwtValidity = jwtValidity;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public Long getDefaultUserAppId() {
        return defaultUserAppId;
    }

    public void setDefaultUserAppId(Long defaultUserAppId) {
        this.defaultUserAppId = defaultUserAppId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Long getDefaultUserRoleId() {
        return defaultUserRoleId;
    }

    public void setDefaultUserRoleId(Long defaultUserRoleId) {
        this.defaultUserRoleId = defaultUserRoleId;
    }

    public Long getDefaultAdminAppId() {
        return defaultAdminAppId;
    }

    public void setDefaultAdminAppId(Long defaultAdminAppId) {
        this.defaultAdminAppId = defaultAdminAppId;
    }

    public Long getDefaultAdminRoleId() {
        return defaultAdminRoleId;
    }

    public void setDefaultAdminRoleId(Long defaultAdminRoleId) {
        this.defaultAdminRoleId = defaultAdminRoleId;
    }
}
