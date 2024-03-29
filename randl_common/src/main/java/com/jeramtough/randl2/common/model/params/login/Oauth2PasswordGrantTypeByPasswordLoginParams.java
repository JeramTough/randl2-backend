package com.jeramtough.randl2.common.model.params.login;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 *
 *     密码授权模式根据密码登录参数
 *
 * Created on 2020/11/17 10:24
 * by @author WeiBoWen
 * </pre>
 */
@Component
@Scope(value = "request")
public class Oauth2PasswordGrantTypeByPasswordLoginParams {


    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "(^[a-z0-9A-Z_]{5,16}$)|" +
            "(^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$)|" +
            "(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",payload = ErrorU.CODE_8.class,
            message = "5-16位长度的账号名 || 手机号码13368696807  ||  邮箱32443@qq.com")
    @ApiModelProperty(value = "登陆凭证信息", example = "15289678163", required = true)
    private String credential;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "^\\S{8,16}$",payload = ErrorU.CODE_8.class,
            message = "密码长度范围在8-16位；只允许非空白任意字符")
    @ApiModelProperty(value = "密码", example = "12345678", required = true)
    private String password;

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
