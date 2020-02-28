package com.jeramtough.randl2.bean.adminuser;

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
@ApiModel("更新当前登录的管理员用户参数")
public class UpdateCurrentAdminUserParams {


    @Pattern(regexp ="^\\S{8,16}$" , message = "1032")
    @ApiModelProperty(value = "密码",example = "password",required = false)
    private String password;

    @ApiModelProperty(value = "手机号码",example = "15289678163",dataType = "String",required =
            false)
    @Pattern(
            regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                    "(18[0-9])|(19[8|9]))\\d{8}$",message = "1035")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱地址",example = "1321312@qq.com",required = false)
    @Email(message = "1036")
    private String emailAddress;


    public UpdateCurrentAdminUserParams() {
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

}
