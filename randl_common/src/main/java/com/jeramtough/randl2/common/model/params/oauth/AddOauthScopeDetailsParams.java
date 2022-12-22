package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
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
 * @since 2021-02-12
 */
@Schema(description = "OauthScopeDetails对象")
public class AddOauthScopeDetailsParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属于的Oauth资源Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long resourceId;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "有效域表达式")
    private String scopeExpression;

    @Schema(description = "描述")
    @NotBlankButNull(payload = ErrorU.CODE_1.class)
    private String description;

    @Schema(description = "是否需要请求用户批准")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Boolean isRequired;


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
