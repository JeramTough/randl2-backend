package com.jeramtough.randl2.common.model.params.user;

import com.jeramtough.jtweb.model.params.BaseConditionParams;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <pre>
 * Created on 2020/10/12 21:50
 * by @author WeiBoWen
 * </pre>
 */
@Schema(description = "条件查询Randl用户参数")
public class ConditionUserParams extends BaseConditionParams {

    @Schema(description = "应用Id", example = "1", required = true)
    private Long appId;

    @Schema(description = "是否查询用户角色Id列表", example = "true", required = false)
    private Boolean hasRoleIds;

    public Boolean getHasRoleIds() {
        return hasRoleIds;
    }

    public void setHasRoleIds(Boolean hasRoleIds) {
        this.hasRoleIds = hasRoleIds;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
