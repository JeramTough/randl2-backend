package com.jeramtough.randl2.bean.adminuser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 17:41
 * by @author JeramTough
 * </pre>
 */
public class RegisterAdminUserParams {

    @NotNull(message = "{'code':1010,'placeholders':['用户名']}")
    @Pattern(regexp = "^[a-z0-9A-Z]{5,16}$", message = "1014")
    private String username;

    @NotNull(message = "{'code':1010,'placeholders':['密码']}")
    @Pattern(regexp ="^\\S{8,16}$" , message = "1012")
    private String password;

    private String repeatedPassword;

    public RegisterAdminUserParams(String username, String password,
                                   String repeatedPassword) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
