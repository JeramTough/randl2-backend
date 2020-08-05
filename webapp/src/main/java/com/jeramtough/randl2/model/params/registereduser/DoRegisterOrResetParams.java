package com.jeramtough.randl2.model.params.registereduser;

import com.jeramtough.randl2.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 17:41
 * by @author JeramTough
 * </pre>
 */
@ApiModel("确定注册或重置用户参数")
public class DoRegisterOrResetParams {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiModelProperty(value = "事务ID", example = "aaaaaaaaa")
    private String transactionId;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @Range(min = 1, max = 2, message = "{'code':" + ErrorU.CODE_5.C + ",'placeholders':[" +
            "'1:手机号码，2:邮箱地址']}")
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
