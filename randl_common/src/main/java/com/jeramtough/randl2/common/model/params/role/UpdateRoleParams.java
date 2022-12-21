package com.jeramtough.randl2.common.model.params.role;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
public class UpdateRoleParams {

    @ApiModelProperty(value = "应用Id", example = "1", required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;

    @ApiModelProperty(value = "角色Id", example = "1", required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long fid;

    @ApiModelProperty(value = "英文别名(不可重复值)")
    @Pattern(regexp = "^([a-zA-Z]|[0-9]|[-]|[_])+$", payload = ErrorU.CODE_4.class,
            message = "英文别名只允许输入英文字母数字或者下划线或减号")
    @NotNull(payload = ErrorU.CODE_1.class)
    private String alias;

    private String name;

    @ApiModelProperty(value = "角色描述", example = "who", required = true)
    @NotBlankButNull(payload = ErrorU.CODE_2.class,isSetNullAuto = true)
    private String description;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
