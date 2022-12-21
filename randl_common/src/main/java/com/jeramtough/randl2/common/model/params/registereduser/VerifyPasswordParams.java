package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("校验该密码是否可用")
public class VerifyPasswordParams {

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "事务ID", example = "aaaaaaaaa")
    private String transactionId;

   @NotNull(payload = ErrorU.CODE_1.class)
   @Range(min = 1, max = 2, payload = ErrorU.CODE_7.class, message = "手机号码，2:邮箱地址")
    @ApiModelProperty(value = "注册方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "密码", example = "12345678")
   @Pattern(regexp = "^\\S{8,16}$",payload = ErrorU.CODE_8.class,
           message = "密码长度范围在8-16位；只允许非空白任意字符")
    private String password;

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "重复密码", example = "12345678")
    private String repeatedPassword;


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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}
