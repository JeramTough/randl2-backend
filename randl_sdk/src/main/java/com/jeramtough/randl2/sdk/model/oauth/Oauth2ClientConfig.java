package com.jeramtough.randl2.sdk.model.oauth;

/**
 * <pre>
 * Created on 2020/11/23 23:55
 * by @author WeiBoWen
 * </pre>
 */
public class Oauth2ClientConfig {

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String userAuthorizationUri;

    private String accessTokenUri;

    private String ssoLoginUrl;

    private String redirectUrisUrl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getSsoLoginUrl() {
        return ssoLoginUrl;
    }

    public void setSsoLoginUrl(String ssoLoginUrl) {
        this.ssoLoginUrl = ssoLoginUrl;
    }

    public String getRedirectUrisUrl() {
        return redirectUrisUrl;
    }

    public void setRedirectUrisUrl(String redirectUrisUrl) {
        this.redirectUrisUrl = redirectUrisUrl;
    }
}
