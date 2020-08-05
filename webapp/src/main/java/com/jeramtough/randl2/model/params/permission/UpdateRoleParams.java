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
public class UpdateRoleParams {

    @ApiModelProperty(value = "角色Id", example = "1", required = true)
    @NotNull(message = ErrorU.CODE_1.C + "")
    private Long fid;

    @Pattern(regexp = "^([a-zA-Z]|[-]|[_])+$", message = "5002")
    private String name;

    private String description;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

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
