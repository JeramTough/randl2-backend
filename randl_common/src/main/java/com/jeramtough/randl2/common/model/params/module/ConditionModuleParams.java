package com.jeramtough.randl2.common.model.params.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel("条件查询Randl模块参数")
public class ConditionModuleParams extends BaseConditionParams {


    @ApiModelProperty(value = "应用Id", example = "1", required = true)
    private Long appId;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
