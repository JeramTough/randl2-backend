package com.jeramtough.randl2.common.model.params.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value="更新RandlApp对象", description="")
public class UpdateAppParams implements Serializable{

    private static final long serialVersionUID=1L;

   @NotNull(payload = ErrorU.CODE_1.class)
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
        return "RandlUserApp{" +
        "fid=" + fid +
        ", appName=" + appName +
        ", appCode=" + appCode +
        ", description=" + description +
        ", isAble=" + isAble +
        "}";
    }
}
