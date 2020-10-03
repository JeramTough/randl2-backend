package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
@ApiModel("通过账号密码登录参数")
public class LoginByPasswordCredentials {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiParam(value = "账号、手机号、邮箱地址", required = true)
    private String credential;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Pattern(regexp = "^\\S{8,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + (",'placeholders':[" +
                    "'密码长度范围在8-16位；只允许非空白任意字符'," +
                    "]}"))
    @ApiParam(value = "密码", required = true)
    private String password;


    public LoginByPasswordCredentials() {
    }

    public LoginByPasswordCredentials(String credential, String password) {
        this.credential = credential;
        this.password = password;
    }

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
