package com.jeramtough.randl2.bean.verificationcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 21:03
 * by @author JeramTough
 * </pre>
 */
@ApiModel("发送验证码参数")
public class SendVerificationCodeParams {

    @NotNull(message = "{'code':667,'placeholders':['发送失败','以手机1或邮箱方式2']}")
    @ApiModelProperty(value = "发送方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

    @NotNull(message = "{'code':667,'placeholders':['发送失败','以手机号码或邮箱地址']}")
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
