package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;
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
public class AddOauthResourceDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @ApiModelProperty(value = "是否是用户资源，用户资源需要用户授权访问")
    private Integer isUserResource;

    private List<AddOauthScopeDetailsParams> scopeDetailsList;


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

    public List<AddOauthScopeDetailsParams> getScopeDetailsList() {
        return scopeDetailsList;
    }

    public void setScopeDetailsList(
            List<AddOauthScopeDetailsParams> scopeDetailsList) {
        this.scopeDetailsList = scopeDetailsList;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
                ", appId=" + appId +
                "}";
    }
}
