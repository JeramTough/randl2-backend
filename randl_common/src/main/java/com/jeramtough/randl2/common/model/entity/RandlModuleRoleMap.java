package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@ApiModel(value="RandlModuleRoleMap对象", description="")
public class RandlModuleRoleMap implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long moduleId;

    private Long roleId;

    private Long appId;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }


    @Override
    public String toString() {
        return "RandlModuleRoleMap{" +
        "fid=" + fid +
        ", moduleId=" + moduleId +
        ", roleId=" + roleId +
        ", appId=" + appId +
        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RandlModuleRoleMap that = (RandlModuleRoleMap) o;

        return moduleId.equals(that.moduleId);
    }

    @Override
    public int hashCode() {
        return moduleId.hashCode();
    }
}
