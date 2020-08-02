package com.jeramtough.randl2.model.params.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
@ApiModel("添加API接口信息参数")
public class AddApiParams {

    @ApiModelProperty(value = "路径", example = "/test/getSomething", required = true)
    @NotNull(message = "{'code':4000,'placeholders':['API路径']}")
    private String path;

    @ApiModelProperty(value = "接口描述", example = "do something", required = false)
    private String description;

    @NotNull(message = "{'code':667,'placeholders':['添加','API别名']}")
    @ApiModelProperty(value = "接口别名", example = "BM", required = true)
    private String alias;

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
}
