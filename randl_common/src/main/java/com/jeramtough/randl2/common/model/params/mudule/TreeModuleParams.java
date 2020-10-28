package com.jeramtough.randl2.common.model.params.mudule;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/10/27 23:31
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel(value="查询RandlModule树形列表参数", description="")
public class TreeModuleParams {

    @ApiModelProperty(value = "AppId",example = "1",required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
