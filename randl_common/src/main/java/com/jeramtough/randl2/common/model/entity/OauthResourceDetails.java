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
 * @since 2021-02-02
 */
@ApiModel(value="OauthResourceDetails对象", description="")
public class OauthResourceDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long appId;

    private String keySetUrl;

    private String checkTokenUrl;

  /**
   * 是否是用户资源，用户资源需要用户授权访问
   */
    private Integer isUserResource;


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
