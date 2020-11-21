package com.jeramtough.randl2.common.model.params.login;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/11/15 0:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel(value = "登陆凭证", description = "")
public class LoginCredentialsParams {

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "(^[a-z0-9A-Z_]{5,16}$)|" +
            "(^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$)|" +
            "(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",payload = ErrorU.CODE_8.class,
            message = "5-16位长度的账号名 || 手机号码13368696807  ||  邮箱32443@qq.com")
    @ApiModelProperty(value = "登陆凭证信息", example = "15289678163", required = true)
    private String credentials;

    @NotNull(payload = ErrorU.CODE_1.class)
    @Pattern(regexp = "^\\S{8,16}$",payload = ErrorU.CODE_8.class,
            message = "密码长度范围在8-16位；只允许非空白任意字符")
    @ApiModelProperty(value = "密码", example = "12345678", required = true)
    private String password;

    @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "Rand应用Id", example = "1", required = true)
    private Long appId;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
