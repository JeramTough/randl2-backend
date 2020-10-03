package com.jeramtough.randl2.common.model.params.adminuser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("注册管理员用户参数")
public class RegisterAdminUserParams {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + ",'placeholders':['用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符']}")
    @ApiModelProperty(value = "用户名", example = "username", required = true)
    private String username;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(regexp = "^\\S{8,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + ",'placeholders':['密码长度范围在8-16位；只允许非空白任意字符']}")
    @ApiModelProperty(value = "密码", example = "password", required = true)
    private String password;

    @ApiModelProperty(value = "手机号码", example = "15289678163", dataType = "String", required =
            false)
    @Pattern(
            regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                    "(18[0-9])|(19[8|9]))\\d{8}$"
            , message = "{'code':" + ErrorU.CODE_2.C + ",'placeholders':["+
            "'例子:15289678164']}")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱地址", example = "1321312@qq.com", required = false)
    @Email(message = "{'code':" + ErrorU.CODE_2.C + ",'placeholders':['例子:1234@qq.com']}")
    private String emailAddress;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiModelProperty(value = "重复密码", example = "password", required = true)
    private String repeatedPassword;

    @ApiModelProperty(value = "角色Id(默认1)", example = "1", required = false)
    private Long roleId;

    public RegisterAdminUserParams() {
    }

    public RegisterAdminUserParams(String username, String password,
                                   String repeatedPassword) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
