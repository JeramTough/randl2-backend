package com.jeramtough.randl2.bean.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/16 21:03
 * by @author JeramTough
 * </pre>
 */
@ApiModel("绑定手机或邮箱参数")
public class BindingPhoneOrEmailParams {

    @NotNull(message = "{'code':667,'placeholders':['发送失败','以手机1或邮箱方式2']}")
    @ApiModelProperty(value = "绑定方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

    @NotNull(message = "{'code':667,'placeholders':['发送失败','以手机号码或邮箱地址']}")
    @ApiModelProperty(value = "手机号码或邮箱地址", example = "15289678163")
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            message = "{'code':668,'placeholders':['需要绑定的号码或者地址','手机号码或者邮箱地址']}")
    private String phoneOrEmail;

    @NotNull(message = "{'code':667,'placeholders':['登录失败','验证码']}")
    @Pattern(regexp = "^[0-9]{6}$", message = "{'code':668,'placeholders':['验证码'," +
            "'6位长度正整数']}")
    @ApiModelProperty(value = "验证码", example = "108764")
    private String verificationCode;

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
