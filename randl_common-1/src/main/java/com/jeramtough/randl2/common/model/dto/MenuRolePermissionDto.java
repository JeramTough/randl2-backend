package com.jeramtough.randl2.common.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-09-14
 */
@ApiModel(value = "MenuRolePermission对象", description = "")
public class MenuRolePermissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @ApiModelProperty(value = "菜单Id")
    private Long menuId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "MenuApiPermission{" +
                "fid=" + fid +
                ", menuId=" + menuId +
                ", apiId=" + roleId +
                "}";
    }
}
