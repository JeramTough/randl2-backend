package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@Schema(description = "OauthClientDetails对象")
public class AddOauthClientDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @Schema(description = "所拥有的资源Ids")
    private String resourceIds;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String authorizedGrantTypes;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String webServerRedirectUris;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Boolean autoApprove;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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


    @Override
    public String toString() {
        return "OauthClientDetails{" +
                ", appId=" + appId +
                ", resourceIds=" + resourceIds +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", webServerRedirectUris=" + webServerRedirectUris +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", autoApprove=" + autoApprove +
                "}";
    }
}
