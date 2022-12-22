package com.jeramtough.randl2.common.model.params.module;

import com.jeramtough.jtweb.model.params.BaseConditionParams;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@Schema(description = "条件查询Randl模块参数")
public class ConditionModuleParams extends BaseConditionParams {


    @Schema(description = "应用Id", example = "1", required = true)
    private Long appId;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
