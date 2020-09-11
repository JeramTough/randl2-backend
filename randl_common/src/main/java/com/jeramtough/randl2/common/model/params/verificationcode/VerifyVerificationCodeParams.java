package com.jeramtough.randl2.common.model.params.verificationcode;

import com.jeramtough.randl2.common.model.error.ErrorU;
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
@ApiModel("校验验证码参数")
public class VerifyVerificationCodeParams {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(regexp = "^[0-9]{6}$", message = "{'code':668,'placeholders':['验证码'," +
            "'6位长度正整数']}")
    @ApiModelProperty(value = "验证码", example = "108764")
    private String verificationCode;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            message = "{'code':" + ErrorU.CODE_2.C + ",'placeholders':[" +
                    "'例子:15289678164或1154@qq.com']}")
    @ApiModelProperty(value = "以手机号码或邮箱地址", example = "15289678163")
    private String phoneOrEmail;

    public VerifyVerificationCodeParams() {
    }

    public VerifyVerificationCodeParams(@NotNull(
            message = "{'code':667,'placeholders':['登录失败','验证码']}") @Pattern(
            regexp = "^[0-9]{6}$", message = "{'code':668,'placeholders':['验证码'," +
            "'6位长度正整数']}") String verificationCode,
                                        @NotNull(
                                                message = "{'code':667,'placeholders':['发送失败','以手机号码或邮箱地址']}") String phoneOrEmail) {
        this.verificationCode = verificationCode;
        this.phoneOrEmail = phoneOrEmail;
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
