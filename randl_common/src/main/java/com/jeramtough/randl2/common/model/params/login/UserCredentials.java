package com.jeramtough.randl2.common.model.params.login;

import com.jeramtough.randl2.common.model.error.ErrorU;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
public class UserCredentials {


   @NotNull(payload = ErrorU.CODE_1.class)
   @Pattern(regexp = "^[a-z0-9A-Z_]{5,16}$",payload = ErrorU.CODE_8.class,
            message = "帐号名长度范围在5-16位；只能为数字或者字母或者下划线_；不能含有特殊字符")
    private String username;

   @NotNull(payload = ErrorU.CODE_1.class)
   @Pattern(regexp = "^\\S{8,16}$",payload = ErrorU.CODE_8.class,
           message = "密码长度范围在8-16位；只允许非空白任意字符")
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String username, String password) {
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

    @Override
    public String toString() {
        return "UserCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
