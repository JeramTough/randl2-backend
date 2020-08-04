package com.jeramtough.randl2.model.params.registereduser;

import com.jeramtough.randl2.model.error.ErrorS;
import com.jeramtough.randl2.model.error.ErrorU;
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
@ApiModel("更新普通注册用户参数")
public class UpdateRegisteredUserParams {

    @NotNull(message = "{'code':667,'placeholders':['更新失败','用户ID']}")
    @ApiModelProperty(value = "用户ID", example = "0", required = true)
    private Long uid;

    @Pattern(regexp = "^[a-z0-9A-Z]{5,12}$", message = "7063")
    @ApiModelProperty(value = "帐号名", example = "account", required = false)
    private String account;

    @Pattern(regexp = "^\\S{8,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + ",'placeholders" +
                    "':['password'," +
                    "'密码长度范围在8-16位；只允许非空白任意字符']}")
    @ApiModelProperty(value = "密码", example = "password", required = false)
    private String password;

    @ApiModelProperty(value = "手机号码", example = "15289678163", dataType = "String", required =
            false)
    @Pattern(
            regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                    "(18[0-9])|(19[8|9]))\\d{8}$", message = "7064")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱地址", example = "1321312@qq.com", required = false)
    @Email(message = "7065")
    private String emailAddress;

    @ApiModelProperty(value = "角色Id", example = "2", required = false)
    private Long roleId;

    public UpdateRegisteredUserParams() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
