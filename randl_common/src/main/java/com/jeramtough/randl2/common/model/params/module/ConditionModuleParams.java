package com.jeramtough.randl2.common.model.params.module;

import com.jeramtough.jtweb.model.params.BaseConditionParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel("条件查询Randl模块参数")
public class ConditionModuleParams extends BaseConditionParams {


    @ApiModelProperty(value = "应用Id", example = "1", required = true)
    private Long appId;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
