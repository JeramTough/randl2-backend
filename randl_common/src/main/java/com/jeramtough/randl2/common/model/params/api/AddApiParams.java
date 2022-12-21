package com.jeramtough.randl2.common.model.params.api;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
@ApiModel("添加API接口信息参数")
public class AddApiParams {

    @ApiModelProperty(value = "路径", example = "/test/getSomething", required = true)
    @NotNull(payload = ErrorU.CODE_1.class)
    private String path;

    @ApiModelProperty(value = "接口描述", example = "do something", required = false)
    @Length(max = 255,payload = ErrorU.CODE_7.class,message = "最长255个字符")
    @NotBlankButNull(payload = ErrorU.CODE_2.class,isSetNullAuto = true)
    private String description;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "接口别名", example = "BM", required = true)
    private String alias;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "属于哪个app的接口")
    private Long appId;

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
