package com.jeramtough.randl2.common.model.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@ApiModel(value="Role对象", description="")
public class RoleDto implements Serializable {

    private static final long serialVersionUID=1L;

    private Long fid;

    private String name;

    private String description;

    private Long appId;


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

    @Override
    public String toString() {
        return "RoleDto{" +
        "fid=" + fid +
        ", name=" + name +
        ", description=" + description +
        "}";
    }
}
