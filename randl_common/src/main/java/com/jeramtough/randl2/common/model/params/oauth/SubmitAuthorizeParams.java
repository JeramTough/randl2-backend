package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.error.ErrorU;
import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2021/2/13 0:26
 * by @author WeiBoWen
 * </pre>
 */
public class SubmitAuthorizeParams {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "临时ClientId", example = "aaac139e605b4a1b8b5f91f780607825")
    private String tempClientId;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "是否授权", example = "false")
    private Boolean isApproved;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "用户确定授予的ScopeId")
    private String scopeIds;


    public String getTempClientId() {
        return tempClientId;
    }

    public void setTempClientId(String tempClientId) {
        this.tempClientId = tempClientId;
    }



    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getScopeIds() {
        return scopeIds;
    }

    public void setScopeIds(String scopeIds) {
        this.scopeIds = scopeIds;
    }
}
