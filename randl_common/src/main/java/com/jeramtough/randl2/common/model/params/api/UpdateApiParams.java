package com.jeramtough.randl2.common.model.params.api;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
@ApiModel("更新API接口信息参数")
public class UpdateApiParams {

    @ApiModelProperty(value = "接口Id", example = "1", required = true)
   @NotNull(payload = ErrorU.CODE_1.class)
    private Long fid;

    @ApiModelProperty(value = "路径", example = "/test/getSomething", required = false)
    private String path;

    @ApiModelProperty(value = "接口描述", example = "do something", required = false)
    private String description;

    @ApiModelProperty(value = "接口别名", example = "BM", required = false)
    private String alias;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "属于哪个app的接口")
    private Long appId;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
