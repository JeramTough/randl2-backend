package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <pre>
 * Created on 2020/10/4 1:45
 * by @author WeiBoWen
 * </pre>
 */
public class RandlModuleAuthDto {

    @Schema(description = "唯一id")
    private Long mid;

    @Schema(description = "模块中文名")
    private String name;

    @Schema(description = "模块描述")
    private String description;

    @Schema(description = "模块路径")
    private String path;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "排序")
    private Integer moduleOrder;

    @Schema(description = "模块图标 默认为斜杠")
    private String icon;

    @Schema(description = "上级模块Id")
    private Long parentModuleId;

    @Schema(description = "模块是否被管理员禁用")
    private Integer isAble;


    @Schema(description = "模块是否被授权")
    private Integer isAuth;

    @Schema(description = "子模块是否有被授权")
    private Integer hasChildAuth;

    @Schema(description = "子模块是否有被管理员禁用")
    private Integer hasChildAble;

    private Long appId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Integer getHasChildAuth() {
        return hasChildAuth;
    }

    public void setHasChildAuth(Integer hasChildAuth) {
        this.hasChildAuth = hasChildAuth;
    }

    public Integer getHasChildAble() {
        return hasChildAble;
    }

    public void setHasChildAble(Integer hasChildAble) {
        this.hasChildAble = hasChildAble;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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

    public Long getParentModuleId() {
        return parentModuleId;
    }

    public void setParentModuleId(Long parentModuleId) {
        this.parentModuleId = parentModuleId;
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


    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    @Override
    public String toString() {
        return "RandlModuleAuthDto{" +
                "mid=" + mid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", level=" + level +
                ", moduleOrder=" + moduleOrder +
                ", icon='" + icon + '\'' +
                ", parentModuleId=" + parentModuleId +
                ", isAble=" + isAble +
                ", isAuth=" + isAuth +
                ", appId=" + appId +
                ", createTime=" + createTime +
                '}';
    }
}
