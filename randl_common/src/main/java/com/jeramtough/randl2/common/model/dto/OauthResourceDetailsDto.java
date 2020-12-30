package com.jeramtough.randl2.common.model.dto;

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
 * @since 2020-11-27
 */
@ApiModel(value="OauthResourceDetails对象", description="")
public class OauthResourceDetailsDto implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long appId;

    private String keySetUrl;

    private String checkTokenUrl;


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

    @Override
    public String toString() {
        return "OauthResourceDetails{" +
        "fid=" + fid +
        ", appId=" + appId +
        ", keySetUrl=" + keySetUrl +
        ", checkTokenUrl=" + checkTokenUrl +
        "}";
    }
}
