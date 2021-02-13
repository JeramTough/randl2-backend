package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-12
 */
@ApiModel(value="OauthScopeDetails对象", description="")
public class OauthScopeDetails implements Serializable {

    private static final long serialVersionUID=1L;

  /**
   * 自增ID
   */
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

  /**
   * 属于的Oauth资源Id
   */
    private Long resourceId;

  /**
   * 有效域表达式
   */
    private String scopeExpression;

  /**
   * 描述
   */
    private String description;

  /**
   * 是否需要请求用户批准
   */
    private Integer isRequired;


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


    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
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
