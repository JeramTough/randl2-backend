package com.jeramtough.randl2.bean.registereduser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
public class RegisteredUserCredentials {

    @NotNull(message = "{'code':1002,'placeholders':['用户名']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$", message = "1003")
    private String credential;

    @NotNull(message = "{'code':1002,'placeholders':['密码']}")
    @Pattern(regexp ="^\\S{8,16}$" , message = "1004")
    private String password;

    private String verificationCode;

    public RegisteredUserCredentials() {
    }

    public RegisteredUserCredentials(String credential, String password) {
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
