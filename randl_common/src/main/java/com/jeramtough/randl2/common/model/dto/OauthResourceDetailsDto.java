package com.jeramtough.randl2.common.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@ApiModel(value="OauthResourceDetails对象", description="")
public class OauthResourceDetailsDto implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long appId;

    private String keySetUrl;

    private String checkTokenUrl;

    @ApiModelProperty(value = "是否是用户资源，用户资源需要用户授权访问")
    private Integer isUserResource;

    @ApiModelProperty(value = "客户端中文名称")
    private String appName;

    @ApiModelProperty(value = "客户端描述")
    private String description;

    @ApiModelProperty(value = "是否可用")
    private Integer isAble;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "0=后台服务端，1=Web前端，2=安卓苹果端，3=小程序端")
    private Integer type;


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

    public String getKeySetUrl() {
        return keySetUrl;
    }

    public void setKeySetUrl(String keySetUrl) {
        this.keySetUrl = keySetUrl;
    }

    public String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
    }

    public Integer getIsUserResource() {
        return isUserResource;
    }

    public void setIsUserResource(Integer isUserResource) {
        this.isUserResource = isUserResource;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OauthResourceDetails{" +
        "fid=" + fid +
        ", appId=" + appId +
        ", keySetUrl=" + keySetUrl +
        ", checkTokenUrl=" + checkTokenUrl +
        ", isUserResource=" + isUserResource +
        "}";
    }
}
