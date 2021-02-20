package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@ApiModel(value = "OauthResourceDetails对象", description = "")
public class UpdateOauthResourceDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long fid;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @ApiModelProperty(value = "是否是用户资源，用户资源需要用户授权访问")
    private Integer isUserResource;

    private List<UpdateOauthScopeDetailsParams> scopeDetailsList;

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

    public Integer getIsUserResource() {
        return isUserResource;
    }

    public void setIsUserResource(Integer isUserResource) {
        this.isUserResource = isUserResource;
    }

    public List<UpdateOauthScopeDetailsParams> getScopeDetailsList() {
        return scopeDetailsList;
    }

    public void setScopeDetailsList(
            List<UpdateOauthScopeDetailsParams> scopeDetailsList) {
        this.scopeDetailsList = scopeDetailsList;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
                ", appId=" + appId +
                "}";
    }
}
