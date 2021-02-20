package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-12
 */
@ApiModel(value = "OauthScopeDetails对象", description = "")
public class UpdateOauthScopeDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fid;

    @ApiModelProperty(value = "属于的Oauth资源Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long resourceId;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "有效域表达式")
    private String scopeExpression;

    @ApiModelProperty(value = "描述")
    @NotBlankButNull(payload = ErrorU.CODE_1.class)
    private String description;

    @ApiModelProperty(value = "是否需要请求用户批准")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Boolean isRequired;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getScopeExpression() {
        return scopeExpression;
    }

    public void setScopeExpression(String scopeExpression) {
        this.scopeExpression = scopeExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }


    @Override
    public String toString() {
        return "OauthScopeDetails{" +
                ", resourceId=" + resourceId +
                ", scopeExpression=" + scopeExpression +
                ", description=" + description +
                ", isRequired=" + isRequired +
                "}";
    }
}
