package com.jeramtough.randl2.common.model.params.module;

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
public class SetModuleApiMapParams {

    @ApiModelProperty(value = "模块Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long moduleId;

    @ApiModelProperty(value = "应用Id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @ApiModelProperty(value = "API的Id")
    private List<Long> apiIds;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<Long> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<Long> apiIds) {
        this.apiIds = apiIds;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
