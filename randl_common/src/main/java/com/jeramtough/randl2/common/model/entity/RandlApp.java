package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-27
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
    private Date createTime;

  /**
   * 0,1,2,3.....0=后台服务端，1=Web前端，2=安卓苹果端，3=小程序端
   */
    private Integer type;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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
        return "RandlApp{" +
        "fid=" + fid +
        ", appName=" + appName +
        ", description=" + description +
        ", isAble=" + isAble +
        ", createTime=" + createTime +
        ", types=" + type +
        "}";
    }
}
