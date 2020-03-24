package com.jeramtough.randl2.bean.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("确定注册用户参数")
public class DoRegisterParams {

    @NotNull(message = "{'code':667,'placeholders':['校验失败','事务ID']}")
    @ApiModelProperty(value = "事务ID", example = "aaaaaaaaa")
    private String transactionId;

    @NotNull(message = "{'code':667,'placeholders':['校验失败','以手机1或邮箱方式注册2']}")
    @ApiModelProperty(value = "注册方式，1:手机号码，2:邮箱地址", example = "1")
    private Integer way;


    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
