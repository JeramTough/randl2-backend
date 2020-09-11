package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.params.permission.AddRoleParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateRoleParams;
import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.common.model.dto.RoleDto;

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

    /**
     * @deprecated 使用{}{@link #getAllAdminRole()}和#{@link #getAllUserRole()} 代替
     * @return 返回角色对象列表
     */
    @Deprecated
    List<RoleDto> getAllRole();

    List<RoleDto> getAllAdminRole();

    List<RoleDto> getAllUserRole();

    List<RoleDto> getRoleListByKeyword(String keyword);
}
