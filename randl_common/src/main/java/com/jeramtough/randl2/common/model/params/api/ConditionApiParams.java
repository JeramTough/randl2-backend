package com.jeramtough.randl2.common.model.params.api;

import com.jeramtough.jtweb.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel("条件查询Randl应用参数")
public class ConditionApiParams extends BaseConditionParams {

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
