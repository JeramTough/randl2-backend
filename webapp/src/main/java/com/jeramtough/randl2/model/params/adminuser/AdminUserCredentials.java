package com.jeramtough.randl2.model.params.adminuser;

import com.jeramtough.randl2.model.error.ErrorU;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
public class AdminUserCredentials {


    @NotNull(message = "{'code':" + ErrorU.CODE_1.C + ",'placeholders':['用户名']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + ",'placeholders':['用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符']}")
    private String username;

    @NotNull(message = "{'code':" + ErrorU.CODE_1.C + ",'placeholders':['密码']}")
    @Pattern(regexp = "^\\S{8,16}$",
            message = "{'code':" + ErrorU.CODE_6.C + ",'placeholders':['密码长度范围在8-16位；只允许非空白任意字符']}")
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
