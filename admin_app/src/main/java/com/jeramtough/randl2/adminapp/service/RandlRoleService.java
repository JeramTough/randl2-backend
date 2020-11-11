package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.params.role.AddRoleParams;
import com.jeramtough.randl2.common.model.params.role.UpdateRoleParams;
import com.jeramtough.randl2.common.service.MyBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlRoleService extends MyBaseService<RandlRole, RandlRoleDto> {

    String addRole(AddRoleParams params);

    String deleteRole(Long fid);

    String updateRole(UpdateRoleParams params);


    List<RandlRoleDto> getRoleListByKeyword(String keyword);

    /**
     * 每个用户在每个应用里的角色
     *
     * @param appId  应用Id
     * @param uid 角色Id
     * @return 如果没有则返回空
     */
    List<RandlRole> getRoleListByAppIdAndUid(Long appId, Long uid);

    List<RandlRoleDto> getListByAppId(Long appId);
}
