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
 * @since 2020-09-16
 */
@ApiModel(value="RandlApp对象", description="")
public class RandlAppDto implements Serializable{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @ApiModelProperty(value = "客户端中文名称")
    private String appName;

    @ApiModelProperty(value = "客户端代码")
    private String appCode;

    @ApiModelProperty(value = "客户端描述")
    private String description;

    @ApiModelProperty(value = "是否可用")
    private Integer isAble;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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

    @Override
    public String toString() {
        return "RandlApp{" +
        "fid=" + fid +
        ", appName=" + appName +
        ", appCode=" + appCode +
        ", description=" + description +
        ", isAble=" + isAble +
        "}";
    }
}
