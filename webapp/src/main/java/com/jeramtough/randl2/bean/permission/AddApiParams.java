package com.jeramtough.randl2.bean.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
@ApiModel("添加API接口参数")
public class AddApiParams {

    @ApiModelProperty(value = "路径", example = "/test/getSomething", required = true)
    @NotNull(message = "{'code':4000,'placeholders':['API路径']}")
    private String path;

    @ApiModelProperty(value = "接口描述", example = "do something", required = true)
    @NotNull(message = "{'code':4000,'placeholders':['接口描述']}")
    private String description;


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
}
