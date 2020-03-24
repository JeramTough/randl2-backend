package com.jeramtough.randl2.bean.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
@ApiModel("通过验证码登录参数")
public class RegisteredUserCredentials2 {

    @NotNull(message = "{'code':1002,'placeholders':['用户名']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$", message = "1003")
    @ApiParam(value = "账号、手机号、邮箱地址", required = true)
    private String credential;

    @NotNull(message = "{'code':667,'placeholders':['登录失败','以手机1或邮箱方式2登录']}")
    @ApiModelProperty(value = "登录方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

    @NotNull(message = "{'code':667,'placeholders':['登录失败','验证码']}")
    @ApiModelProperty(value = "验证码", example = "108764")
    private String verificationCode;

    public RegisteredUserCredentials2() {
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
