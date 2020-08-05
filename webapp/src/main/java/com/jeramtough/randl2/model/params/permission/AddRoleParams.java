package com.jeramtough.randl2.model.params.permission;

import com.jeramtough.randl2.model.error.ErrorU;
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
    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(regexp = "^([a-zA-Z]|[-]|[_])+$", message = "5002")
    private String name;

    @ApiModelProperty(value = "角色描述", example = "who", required = true)
    @NotNull(message = ErrorU.CODE_1.C + "")
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
