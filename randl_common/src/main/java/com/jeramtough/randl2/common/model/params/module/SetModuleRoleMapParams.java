package com.jeramtough.randl2.common.model.params.module;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * Created on 2020/11/1 1:08
 * by @author WeiBoWen
 * </pre>
 */
public class SetModuleRoleMapParams {

    @ApiModelProperty(value = "角色Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long roleId;

    @ApiModelProperty(value = "应用Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @ApiModelProperty(value = "API的Id")
    private List<Long> moduleIds;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<Long> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
