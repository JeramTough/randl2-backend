package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.permission.AddRoleParams;
import com.jeramtough.randl2.bean.permission.UpdateRoleParams;
import com.jeramtough.randl2.model.entity.Role;
import com.jeramtough.randl2.model.dto.RoleDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RoleService extends BaseService<Role,RoleDto> {

    String addRole(AddRoleParams params);

    String deleteRole(Long fid);

    String updateRole(UpdateRoleParams params);

    RoleDto getRole(Long fid);

    List<RoleDto> getAllRole();

    List<RoleDto> getRoleListByKeyword(String keyword);
}
