package com.jeramtough.randl2.common.model.params.oauth;



import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * <pre>
 *     授权码模式授权结果
 * Created on 2021/2/13 3:59
 * by @author WeiBoWen
 * </pre>
 */
public class OauthAuthorizeResult4AuthorizationCodeGrant implements Serializable {

    private static final long serialVersionUID = 5308548402600578526L;

    @Schema(description = "授权重定向uri")
    private String redirectUri;

    @Schema(description = "是否授权")
    private Boolean isApproved;

    @Schema(description = "授权码模式code")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
