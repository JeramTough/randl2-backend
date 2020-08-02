package com.jeramtough.randl2.service;

import com.jeramtough.randl2.model.params.permission.PermissionParams;
import com.jeramtough.randl2.model.entity.Permission;
import com.jeramtough.randl2.model.dto.PermissionDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface PermissionService extends BaseService<Permission,PermissionDto> {

    String setPermissions(PermissionParams permissionParams);

    @Deprecated
    String removePermissions(PermissionParams permissionParams);

    List<PermissionDto> getPermissions();

    List<PermissionDto> getPermissionListByRoleId(Long roleId);
}
