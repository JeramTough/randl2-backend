package com.jeramtough.randl2.common.model.entity;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Schema(description="Permission对象")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer fid;

    private Integer apiId;

    private Integer roleId;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Permission{" +
        "fid=" + fid +
        ", apiId=" + apiId +
        ", roleId=" + roleId +
        "}";
    }
}
