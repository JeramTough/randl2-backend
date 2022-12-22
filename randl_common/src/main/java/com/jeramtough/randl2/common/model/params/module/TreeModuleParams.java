package com.jeramtough.randl2.common.model.params.module;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/10/27 23:31
 * by @author WeiBoWen
 * </pre>
 */
@Schema(description="查询RandlModule树形列表参数")
public class TreeModuleParams {

    @Schema(description = "AppId",example = "1",required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @Schema(description = "模块Id,设置则从这个module开始")
    private Long moduleId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
