package com.jeramtough.randl2.common.model.params.oauth;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <pre>
 *     隐式模式授权结果
 * Created on 2021/2/13 3:59
 * by @author WeiBoWen
 * </pre>
 */
public class OauthAuthorizeResult4ImplicitGrant implements Serializable {

    private static final long serialVersionUID = -3275451913462043145L;

    @ApiModelProperty(value = "授权重定向uri")
    private String redirectUri;

    @ApiModelProperty(value = "是否授权")
    private Boolean isApproved;

    @ApiModelProperty(value = "隐式模式token")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @ApiModelProperty(value = "令牌到期时间")
    private Long expiresIn;

    private String scope;

    private String state;

    private String error;

    private String errorDescription;

    private String additionalInfo;


    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
