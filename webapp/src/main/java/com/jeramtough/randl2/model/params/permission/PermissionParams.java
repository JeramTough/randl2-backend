package com.jeramtough.randl2.model.params.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/1 13:39
 * by @author JeramTough
 * </pre>
 */
@ApiModel("为角色设置接口权限参数")
public class PermissionParams {

    @NotNull(message = "3000")
    @ApiModelProperty(value = "角色Id", required = true, example = "1")
    private Long roleId;

    @ApiModelProperty(value = "API的id数组", example = "[1,2,3]")
    private Long[] apiIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getApiIds() {
        return apiIds;
    }

    public void setApiIds(Long[] apiIds) {
        this.apiIds = apiIds;
    }
}
