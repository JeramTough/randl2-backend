package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleRoleMapDto;
import com.jeramtough.randl2.common.model.entity.RandlModuleRoleMap;
import com.jeramtough.randl2.common.model.params.module.SetModuleRoleMapParams;
import com.jeramtough.randl2.common.service.MyBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public interface RandlModuleRoleMapService extends MyBaseService<RandlModuleRoleMap, RandlModuleRoleMapDto> {


    List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleId(Long appId, Long roleId);

    List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleIds(Long appId, List<Long> roleIds);

    List<Map<String, Object>> getRandlModuleAuthTreeByAppIdAndRoleId(Long appId, Long roleId);

    List<Map<String, Object>> getRandlModuleAuthTreeByAppIdAndRoleIds(Long appId, List<Long> roleIds);

    String setModuleRoleMap(SetModuleRoleMapParams params);
}
