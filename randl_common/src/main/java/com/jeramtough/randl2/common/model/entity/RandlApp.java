package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value="RandlApp对象", description="")
public class RandlApp implements Serializable {

    private static final long serialVersionUID=1L;

  /**
   * 主键
   */
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

  /**
   * 客户端中文名称
   */
    private String appName;

  /**
   * 客户端描述
   */
    private String description;

  /**
   * 是否可用
   */
    private Integer isAble;

  /**
   * 创建时间
   */
    private LocalDateTime createTime;


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

    @Override
    public String toString() {
        return "RandlUserApp{" +
        "fid=" + fid +
        ", appName=" + appName +
        ", description=" + description +
        ", isAble=" + isAble +
        ", createTime=" + createTime +
        "}";
    }
}
