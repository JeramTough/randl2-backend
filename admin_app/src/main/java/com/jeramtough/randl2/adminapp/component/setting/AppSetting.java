package com.jeramtough.randl2.adminapp.component.setting;

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

    @Value(value = "${app.setting.user.defaultAppId}")
    private Long userDefaultAppId;

    @Value(value = "${app.setting.user.defaultRoleId}")
    private Long userDefaultRoleId;

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

    public Long getUserDefaultAppId() {
        return userDefaultAppId;
    }

    public void setUserDefaultAppId(Long userDefaultAppId) {
        this.userDefaultAppId = userDefaultAppId;
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

    public Long getUserDefaultRoleId() {
        return userDefaultRoleId;
    }

    public void setUserDefaultRoleId(Long userDefaultRoleId) {
        this.userDefaultRoleId = userDefaultRoleId;
    }
}
