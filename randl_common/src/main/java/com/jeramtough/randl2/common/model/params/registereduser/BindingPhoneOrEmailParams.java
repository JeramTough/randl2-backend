package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

/**
 * <pre>
 * Created on 2020/2/16 21:03
 * by @author JeramTough
 * </pre>
 */
@Schema(description = "绑定手机或邮箱参数")
public class BindingPhoneOrEmailParams {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Range(min = 1, max = 2, payload = ErrorU.CODE_7.class, message = "手机号码，2:邮箱地址")
    @Schema(description = "绑定方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "手机号码或邮箱地址", example = "15289678163")
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            payload = ErrorU.CODE_4.class, message = "例子:15289678164或1154@qq.com 或者 15289678169")
    private String phoneOrEmail;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "验证码", example = "108764")
    @Pattern(
            regexp = "^[0-9]{6}$", payload = ErrorU.CODE_8.class, message = "6位长度正整数")
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
