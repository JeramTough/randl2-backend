package com.jeramtough.randl2.common.model.params.user;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * Created on 2020/11/1 1:08
 * by @author WeiBoWen
 * </pre>
 */
public class SetUserRoleMapParams {

    @ApiModelProperty(value = "用户Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long uid;


    @ApiModelProperty(value = "角色的Id")
    private List<Long> roleIds;



    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
