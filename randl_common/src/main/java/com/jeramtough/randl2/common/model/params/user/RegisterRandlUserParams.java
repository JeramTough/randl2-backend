package com.jeramtough.randl2.common.model.params.user;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


/**
 * <pre>
 * Created on 2020/1/30 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("注册管理员用户参数")
public class RegisterRandlUserParams {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,16}$", payload = ErrorU.CODE_8.class,
            message = "帐号名长度范围在5-16位；只能为数字或者字母或者下划线_；不能含有特殊字符")
    @ApiModelProperty(value = "账号名", example = "account", required = true)
    private String account;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "^\\S{8,16}$", payload = ErrorU.CODE_8.class,
            message = "密码长度范围在8-16位；只允许非空白任意字符")
    @ApiModelProperty(value = "密码", example = "password", required = true)
    private String password;

    @ApiModelProperty(value = "手机号码", example = "15289678163", dataType = "String",
            required =
                    false)
    @Pattern(
            regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                    "(18[0-9])|(19[8|9]))\\d{8}$"
            , payload = ErrorU.CODE_4.class, message = "例子:15289678164")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱地址", example = "1321312@qq.com", required = false)
//    @Email(payload = ErrorU.CODE_4.class, message = "例子:1171867004@qq.com")
    @Email(payload = {ErrorU.CODE_4.class})
    private String emailAddress;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "重复密码", example = "password", required = true)
    private String repeatedPassword;

    public RegisterRandlUserParams() {
    }

    public RegisterRandlUserParams(String account, String password,
                                   String repeatedPassword) {
        this.account = account;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

}
