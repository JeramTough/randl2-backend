package com.jeramtough.randl2.model.params.adminuser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
public class AdminUserCredentials {


    @NotNull(message = "{'code':1002,'placeholders':['用户名']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$", message = "1003")
    private String username;

    @NotNull(message = "{'code':1002,'placeholders':['密码']}")
    @Pattern(regexp ="^\\S{8,16}$" , message = "1004")
    private String password;

    public AdminUserCredentials() {
    }

    public AdminUserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
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
}
