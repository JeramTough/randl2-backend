package com.jeramtough.randl2.bean.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("校验该密码是否可用")
public class VerifyPasswordParams {

    @NotNull(message = "{'code':667,'placeholders':['','密码']}")
    @ApiModelProperty(value = "密码", example = "12345678")
    @Pattern(regexp = "^\\S{8,16}$", message = "7021")
    private String password;

    @NotNull(message = "{'code':667,'placeholders':['','重复密码']}")
    @ApiModelProperty(value = "重复密码", example = "12345678")
    private String repeatedPassword;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
