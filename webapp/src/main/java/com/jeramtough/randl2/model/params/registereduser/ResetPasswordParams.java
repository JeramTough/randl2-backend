package com.jeramtough.randl2.model.params.registereduser;

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
@ApiModel("重置密码参数")
public class ResetPasswordParams {


    @NotNull(message = "{'code':667,'placeholders':['校验失败','密码']}")
    @ApiModelProperty(value = "旧密码", example = "12345678")
    @Pattern(regexp = "^\\S{8,16}$", message = "7021")
    private String oldPassword;

    @NotNull(message = "{'code':667,'placeholders':['校验失败','密码']}")
    @ApiModelProperty(value = "新密码", example = "12345678")
    @Pattern(regexp = "^\\S{8,16}$", message = "7021")
    private String newPassword;

    @NotNull(message = "{'code':667,'placeholders':['','重复密码']}")
    @ApiModelProperty(value = "重复新密码", example = "12345678")
    private String repeatedNewPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

}
