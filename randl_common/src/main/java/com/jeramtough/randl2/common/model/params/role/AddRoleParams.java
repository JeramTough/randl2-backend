package com.jeramtough.randl2.common.model.params.role;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
public class AddRoleParams {

    @Schema(description = "角色名", example = "TEST", required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private String name;

    @Schema(description = "所属app的id")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;


    @Schema(description = "英文别名(不可重复值)")
    @Pattern(regexp = "^([a-zA-Z]|[0-9]|[-]|[_])+$", payload = ErrorU.CODE_4.class,
            message = "英文别名只允许输入英文字母数字或者下划线或减号")
    @NotNull(payload = ErrorU.CODE_1.class)
    private String alias;

    @Schema(description = "角色描述", example = "who", required = true)
    @NotBlankButNull(payload = ErrorU.CODE_2.class,isSetNullAuto = true)
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
