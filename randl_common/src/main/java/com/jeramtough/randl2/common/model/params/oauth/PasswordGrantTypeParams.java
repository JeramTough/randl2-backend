package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/17 10:24
 * by @author WeiBoWen
 * </pre>
 */
@Component
@Scope(value = "request")
public class PasswordGrantTypeParams {

    private final HttpServletRequest request;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientId;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientSecret;

    @NotBlankButNull(payload = ErrorU.CODE_2.class)
    private String username;

    @NotBlankButNull(payload = ErrorU.CODE_2.class)
    private String password;

    @NotBlankButNull(payload = ErrorU.CODE_2.class)
    @Pattern(regexp = "^[0-9]{6}$", payload = ErrorU.CODE_4.class, message = "6位长度正整数")
    @Schema(description = "验证码", example = "108764")
    private String verificationCode;

    @NotBlankButNull(payload = ErrorU.CODE_2.class)
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            payload = ErrorU.CODE_4.class, message = "例子:15289678164或1154@qq.com 或者 15289678169")
    @Schema(description = "以手机号码或邮箱地址", example = "15289678163")
    private String phoneOrEmail;

    @Autowired
    public PasswordGrantTypeParams(HttpServletRequest request) {
        this.request = request;

        clientId = this.request.getParameter("client_id");
        clientSecret = this.request.getParameter("client_secret");
        username = this.request.getParameter("username");
        password = this.request.getParameter("password");
        verificationCode = this.request.getParameter("verificationCode");
        phoneOrEmail = this.request.getParameter("phoneOrEmail");

        if (clientId != null) {
            clientId = clientId.trim();
        }
        if (username != null) {
            username = username.trim();
        }
        if (password != null) {
            password = password.trim();
        }
        if (phoneOrEmail != null) {
            phoneOrEmail = phoneOrEmail.trim();
        }
        if (verificationCode != null) {
            verificationCode = verificationCode.trim();
        }

    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
