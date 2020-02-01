package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.AddRoleParams;
import com.jeramtough.randl2.bean.permission.UpdateRoleParams;
import com.jeramtough.randl2.dao.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.RoleDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RoleService extends IService<Role> {

    String addRole(AddRoleParams params);

    String deleteRole(Long roleId);

    String updateRole(UpdateRoleParams params);

    RoleDto getRole(Long roleId);

    List<RoleDto> getAllRole();
}
