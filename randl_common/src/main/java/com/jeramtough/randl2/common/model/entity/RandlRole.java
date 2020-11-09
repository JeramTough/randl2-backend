package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@ApiModel(value = "RandlRole对象", description = "")
public class RandlRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    /**
     * 中文名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属app的id
     */
    private Long appId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 英文别名(不可重复值)
     */
    private String alias;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "RandlRole{" +
                "fid=" + fid +
                ", name=" + name +
                ", description=" + description +
                ", appId=" + appId +
                ", createTime=" + createTime +
                ", alias=" + alias +
                "}";
    }
}
