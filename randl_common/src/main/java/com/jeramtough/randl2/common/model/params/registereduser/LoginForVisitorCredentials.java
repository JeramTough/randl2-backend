package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
@ApiModel("游客登录参数")
public class LoginForVisitorCredentials {

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiParam(value = "游客身份账号", required = true)
    private String credential;

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiParam(value = "游客身份密码", required = true)
    private String password;


    public LoginForVisitorCredentials() {
    }

    public LoginForVisitorCredentials(String credential, String password) {
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
