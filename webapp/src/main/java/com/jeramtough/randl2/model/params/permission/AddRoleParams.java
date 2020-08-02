package com.jeramtough.randl2.model.params.permission;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
public class AddRoleParams {

    @ApiModelProperty(value = "角色名", example = "TEST", required = true)
    @NotNull(message = "{'code':5000,'placeholders':['角色名']}")
    @Pattern(regexp = "^([a-zA-Z]|[-]|[_])+$", message = "5002")
    private String name;

    @ApiModelProperty(value = "角色描述", example = "who", required = true)
    @NotNull(message = "{'code':5000,'placeholders':['角色描述']}")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
