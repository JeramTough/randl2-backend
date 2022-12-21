package com.jeramtough.randl2.common.model.params.registereduser;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("确定注册或重置用户参数")
public class DoRegisterOrResetParams {

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "事务ID", example = "aaaaaaaaa")
    private String transactionId;

   @NotNull(payload = ErrorU.CODE_1.class)
   @Range(min = 1, max = 2, payload = ErrorU.CODE_7.class, message = "手机号码，2:邮箱地址")
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
