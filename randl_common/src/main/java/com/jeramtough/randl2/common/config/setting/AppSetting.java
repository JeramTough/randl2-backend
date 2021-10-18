package com.jeramtough.randl2.common.config.setting;

import com.jeramtough.randl2.common.config.setting.api.ApiSetting;
import com.jeramtough.randl2.common.config.setting.path.PathSetting;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/9/28 14:38
 * by @author WeiBoWen
 * </pre>
 */
@Component
@ConfigurationProperties(prefix = "app.setting")
public class AppSetting {

    private Map<String, String> jwt;

    private Map<String, String> admin;

    private Map<String, String> user;

    private Map<String, String> oauth;

    private Map<String, String> sso;

    private PathSetting path;

    private ApiSetting api;


    public AppSetting() {
        jwt = new HashMap<>(6);
        admin = new HashMap<>(6);
        user = new HashMap<>(6);
        oauth = new HashMap<>(6);
        sso = new HashMap<>(6);
    }

    public String getJwtSigningKey() {
        return jwt.get("signingKey");
    }


    public Long getJwtValidity() {
        return new Long(jwt.get("validity"));
    }

    public Long getJwtSsoValidity() {
        return new Long(jwt.get("ssoValidity"));
    }


    public String getJwtIssuer() {
        return jwt.get("issuer");
    }


    public Long getDefaultUserAppId() {
        return Long.parseLong(user.get("defaultAppId"));
    }

    public String getAdminAccount() {
        return admin.get("account");
    }

    public String getAdminPassword() {
        return admin.get("password");
    }


    public Long getDefaultUserRoleId() {
        return Long.parseLong(user.get("defaultRoleId"));
    }


    public Long getDefaultAdminAppId() {
        return Long.parseLong(admin.get("defaultAppId"));
    }


    public Long getDefaultAdminRoleId() {
        return Long.parseLong(admin.get("defaultRoleId"));
    }


    public Map<String, String> getJwt() {
        return jwt;
    }

    public void setJwt(Map<String, String> jwt) {
        this.jwt = jwt;
    }

    public Map<String, String> getAdmin() {
        return admin;
    }

    public void setAdmin(Map<String, String> admin) {
        this.admin = admin;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public Map<String, String> getOauth() {
        return oauth;
    }

    public void setOauth(Map<String, String> oauth) {
        this.oauth = oauth;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public Long getOauthResourceId() {
        return Long.parseLong(oauth.get("resource_id"));
    }

    public void setSso(Map<String, String> sso) {
        this.sso = sso;
    }

    public Map<String, String> getSso() {
        return sso;
    }

    public String getLoginPageUrl() {
        return sso.get("loginPageUrl");
    }

    public PathSetting getPath() {
        return path;
    }

    public void setPath(PathSetting path) {
        this.path = path;
    }

    public ApiSetting getApi() {
        return api;
    }

    public void setApi(ApiSetting api) {
        this.api = api;
    }
}
