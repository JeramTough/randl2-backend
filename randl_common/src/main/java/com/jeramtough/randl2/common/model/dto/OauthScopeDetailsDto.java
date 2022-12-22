package com.jeramtough.randl2.common.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-12
 */
@Schema(description="OauthScopeDetails对象")
public class OauthScopeDetailsDto implements Serializable{

    private static final long serialVersionUID=1L;

    @Schema(description = "自增ID")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @Schema(description = "属于的Oauth资源Id")
    private Long resourceId;

    @Schema(description = "有效域表达式")
    private String scopeExpression;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "是否需要请求用户批准")
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
        "fid=" + fid +
        ", resourceId=" + resourceId +
        ", scopeExpression=" + scopeExpression +
        ", description=" + description +
        ", isRequired=" + isRequired +
        "}";
    }
}
