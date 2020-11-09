package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleRoleMapDto;
import com.jeramtough.randl2.common.model.entity.RandlModuleRoleMap;
import com.jeramtough.randl2.common.service.MyBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public interface RandlModuleRoleMapService extends MyBaseService<RandlModuleRoleMap, RandlModuleRoleMapDto> {

    List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndUid(Long appId, Long uid);

    List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleId(Long appId, Long roleId);

    List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleIds(Long appId, List<Long> roleIds);

}
