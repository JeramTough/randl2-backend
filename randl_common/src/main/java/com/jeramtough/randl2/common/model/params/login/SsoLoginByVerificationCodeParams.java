package com.jeramtough.randl2.common.model.params.login;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/11/15 0:50
 * by @author WeiBoWen
 * </pre>
 */
@Schema(description = "验证码登陆凭证")
public class SsoLoginByVerificationCodeParams {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "^[0-9]{6}$", payload = ErrorU.CODE_4.class, message = "6位长度正整数")
    @Schema(description = "验证码", example = "108764")
    private String verificationCode;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            payload = ErrorU.CODE_4.class, message = "例子:15289678164或1154@qq.com 或者 15289678169")
    @Schema(description = "以手机号码或邮箱地址", example = "15289678163")
    private String phoneOrEmail;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

}
