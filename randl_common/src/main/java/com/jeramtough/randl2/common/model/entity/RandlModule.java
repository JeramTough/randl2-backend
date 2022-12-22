package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@Schema(description="RandlModule对象")
public class RandlModule implements Serializable {

    private static final long serialVersionUID=1L;

  /**
   * 主键
   */
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

  /**
   * 菜单中文名
   */
    private String name;

  /**
   * 菜单描述
   */
    private String description;

  /**
   * 菜单路径
   */
    private String path;

  /**
   * 层级
   */
    private Integer level;

  /**
   * 排序
   */
    private Integer moduleOrder;

  /**
   * 菜单图标 默认为斜杠
   */
    private String icon;

  /**
   * 上级菜单Id
   */
    private Long parentId;

  /**
   * 是否可用
   */
    private Integer isAble;

    private Long appId;

    private Date createTime;


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RandlModule{" +
        "fid=" + fid +
        ", name=" + name +
        ", description=" + description +
        ", url=" + path +
        ", level=" + level +
        ", order=" + moduleOrder +
        ", icon=" + icon +
        ", parentId=" + parentId +
        ", isAble=" + isAble +
        ", appId=" + appId +
        ", createTime=" + createTime +
        "}";
    }
}
