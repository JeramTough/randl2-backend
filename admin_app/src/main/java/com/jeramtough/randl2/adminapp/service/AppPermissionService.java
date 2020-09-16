package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.params.permission.PermissionParams;
import com.jeramtough.randl2.common.model.entity.Permission;
import com.jeramtough.randl2.common.model.dto.PermissionDto;
import com.jeramtough.randl2.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface AppPermissionService extends BaseService<Permission,PermissionDto> {

    String setPermissions(PermissionParams permissionParams);

    @Deprecated
    String removePermissions(PermissionParams permissionParams);

    List<PermissionDto> getPermissions();

    List<PermissionDto> getPermissionListByRoleId(Long roleId);
}
