package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
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
public interface RandlRoleService extends BaseDtoService<RandlRole, RandlRoleDto> {

    String addRole(AddRoleParams params);

    String deleteRole(Long fid);

    String updateRole(UpdateRoleParams params);

    RandlRoleDto getRole(Long fid);

    List<RandlRoleDto> getAllRole();

    List<RandlRoleDto> getRoleListByKeyword(String keyword);

    /**
     * 每个用户在每个应用里，只允许是一个角色
     *
     * @param appId  应用Id
     * @param uid 角色Id
     * @return 如果没有则返回空
     */
    RandlRoleDto getRoleByAppIdAndUid(Long appId, Long uid);
}
