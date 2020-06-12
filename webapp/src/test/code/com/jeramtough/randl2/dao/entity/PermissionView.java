package com.jeramtough.randl2.model.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@ApiModel(value="PermissionView对象", description="VIEW")
public class PermissionView implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer permissionId;

    private Integer roleId;

    private String roleName;

    private String roleDescription;

    private Integer apiId;

    private String apiPath;

    private String apiDescription;


    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

    @Override
    public String toString() {
        return "PermissionView{" +
        "permissionId=" + permissionId +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", roleDescription=" + roleDescription +
        ", apiId=" + apiId +
        ", apiPath=" + apiPath +
        ", apiDescription=" + apiDescription +
        "}";
    }
}
