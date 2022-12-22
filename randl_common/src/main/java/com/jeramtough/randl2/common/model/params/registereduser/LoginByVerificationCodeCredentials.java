package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
@Schema(description = "通过验证码登录参数")
public class LoginByVerificationCodeCredentials {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            payload = ErrorU.CODE_4.class,
            message = "例子:15289678164或1154@qq.com 或者 15289678169")
    @Schema(description = "手机号、邮箱地址", required = true)
    private String credential;

    @NotNull(message = "{'code':667,'placeholders':['登录失败','验证码']}")
    @Schema(description = "验证码", example = "108764", required = true)
    @Pattern(
            regexp = "^[0-9]{6}$", payload = ErrorU.CODE_4.class,
            message = "6位长度正整数")
    private String verificationCode;

    public LoginByVerificationCodeCredentials() {
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
