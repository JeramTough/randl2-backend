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
 * @since 2020-09-16
 */
@ApiModel(value="Api对象", description="")
public class AppApi implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @ApiModelProperty(value = "url路径值")
    private String path;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "英文别名")
    private String alias;

    @ApiModelProperty(value = "属于哪个app的接口")
    private Long appId;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "接口方法json数组")
    private String methods;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "AppApi{" +
        "fid=" + fid +
        ", path=" + path +
        ", description=" + description +
        ", alias=" + alias +
        ", appId=" + appId +
        ", groupName=" + groupName +
        "}";
    }
}
