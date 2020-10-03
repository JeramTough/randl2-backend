package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.entity.RandRole;
import com.jeramtough.randl2.common.model.params.permission.AddRoleParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateRoleParams;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlRoleService extends BaseDtoService<RandRole, RandlRoleDto> {

    String addRole(AddRoleParams params);

    String deleteRole(Long fid);

    String updateRole(UpdateRoleParams params);

    RandlRoleDto getRole(Long fid);

    /**
     * @deprecated 使用{}{@link #getAllAdminRole()}和#{@link #getAllUserRole()} 代替
     * @return 返回角色对象列表
     */
    @Deprecated
    List<RandlRoleDto> getAllRole();

    List<RandlRoleDto> getAllAdminRole();

    List<RandlRoleDto> getAllUserRole();

    List<RandlRoleDto> getRoleListByKeyword(String keyword);
}
