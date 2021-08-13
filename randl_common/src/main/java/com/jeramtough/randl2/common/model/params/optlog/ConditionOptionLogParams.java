package com.jeramtough.randl2.common.model.params.optlog;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.jtweb.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;

/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@ApiModel("条件查询OptionLog参数")
public class ConditionOptionLogParams extends BaseConditionParams {

    @NotBlankButNull(payload = ErrorU.CODE_1.class, isSetNullAuto = true)
    private String adminIdOrName;

    public String getAdminIdOrName() {
        return adminIdOrName;
    }

    public void setAdminIdOrName(String adminIdOrName) {
        this.adminIdOrName = adminIdOrName;
    }
}
