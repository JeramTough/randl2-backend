package com.jeramtough.randl2.model.params.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("校验需要重置密码的账号是否可用")
public class VerifyPhoneOrEmailByForgetParams {

    @NotNull(message = "{'code':667,'placeholders':['校验失败','以手机1或邮箱方式注册2']}")
    @ApiModelProperty(value = "注册方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

    @NotNull(message = "{'code':667,'placeholders':['校验失败','以手机号码或邮箱地址']}")
    @ApiModelProperty(value = "以手机号码或邮箱地址", example = "15289678163")
    private String phoneOrEmail;

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
}
