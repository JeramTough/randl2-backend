package com.jeramtough.randl2.common.model.params.role;

import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel("条件查询Randl角色参数")
public class ConditionRoleParams extends BaseConditionParams {

    @ApiModelProperty(value = "应用Id", example = "1", required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;





    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
