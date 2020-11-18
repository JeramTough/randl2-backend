package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-16
 */
@ApiModel(value="OauthClientDetails对象", description="")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long appId;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scopes;

    private String authorizedGrantTypes;

    private String webServerRedirectUris;

    private String authorities;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private String additionalInformationContent;

    private Boolean autoApprove;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUris() {
        return webServerRedirectUris;
    }

    public void setWebServerRedirectUris(String webServerRedirectUris) {
        this.webServerRedirectUris = webServerRedirectUris;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformationContent() {
        return additionalInformationContent;
    }

    public void setAdditionalInformationContent(String additionalInformationContent) {
        this.additionalInformationContent = additionalInformationContent;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
        "fid=" + fid +
        ", appId=" + appId +
        ", clientId=" + clientId +
        ", resourceIds=" + resourceIds +
        ", clientSecret=" + clientSecret +
        ", scopes=" + scopes +
        ", authorizedGrantTypes=" + authorizedGrantTypes +
        ", webServerRedirectUris=" + webServerRedirectUris +
        ", authorities=" + authorities +
        ", accessTokenValidity=" + accessTokenValidity +
        ", refreshTokenValidity=" + refreshTokenValidity +
        ", additionalInformationContent=" + additionalInformationContent +
        ", autoApprove=" + autoApprove +
        "}";
    }
}
