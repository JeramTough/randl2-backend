package com.jeramtough.randl2.client.model.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */

public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    private Long fid;

    private Long apiId;

    private Long roleId;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
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
