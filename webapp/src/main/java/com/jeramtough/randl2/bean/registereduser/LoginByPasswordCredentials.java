package com.jeramtough.randl2.bean.registereduser;

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

    @NotNull(message = "{'code':1002,'placeholders':['登录凭证']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$", message = "1003")
    @ApiParam(value = "账号、手机号、邮箱地址", required = true)
    private String credential;

    @NotNull(message = "{'code':1002,'placeholders':['密码']}")
    @Pattern(regexp = "^\\S{8,16}$", message = "1004")
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
