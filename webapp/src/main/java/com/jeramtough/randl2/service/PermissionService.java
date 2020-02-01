package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.permission.PermissionParams;
import com.jeramtough.randl2.dao.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.PermissionDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface PermissionService extends IService<Permission> {

    String addPermissions(PermissionParams permissionParams);

    String removePermissions(PermissionParams permissionParams);

    List<PermissionDto> getPermissions();
}
