package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

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
public class RandlModuleDto implements Serializable{

    private static final long serialVersionUID=1L;

    @Schema(description = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @Schema(description = "菜单中文名")
    private String name;

    @Schema(description = "菜单描述")
    private String description;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "排序")
    private Integer moduleOrder;

    @Schema(description = "菜单图标 默认为斜杠")
    private String icon;

    @Schema(description = "上级菜单Id")
    private Long parentId;

    @Schema(description = "是否可用")
    private Integer isAble;

    @Schema(description = "AppId")
    private Long appId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间(格式化后)")
    private String myCreateTime;


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

    public void setModuleOrder(Integer mudoleOrder) {
        this.moduleOrder = mudoleOrder;
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

    public String getMyCreateTime() {
        return myCreateTime;
    }

    public void setMyCreateTime(String myCreateTime) {
        this.myCreateTime = myCreateTime;
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
