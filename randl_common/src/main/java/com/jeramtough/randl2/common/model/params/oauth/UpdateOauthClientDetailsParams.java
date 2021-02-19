package com.jeramtough.randl2.common.model.params.oauth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@ApiModel(value = "OauthClientDetails对象", description = "")
public class UpdateOauthClientDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long fid;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientId;

    private String clientSecret;

    @ApiModelProperty(value = "所拥有的资源Ids")
    private String resourceIds;

    private String authorizedGrantTypes;

    private String webServerRedirectUris;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private Boolean autoApprove;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Boolean iisUpdateSecret;

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

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

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Boolean getIisUpdateSecret() {
        return iisUpdateSecret;
    }

    public void setIisUpdateSecret(Boolean iisUpdateSecret) {
        this.iisUpdateSecret = iisUpdateSecret;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
                "fid=" + fid +
                ", appId=" + appId +
                ", clientId=" + clientId +
                ", resourceIds=" + resourceIds +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", webServerRedirectUris=" + webServerRedirectUris +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", autoApprove=" + autoApprove +
                "}";
    }
}
