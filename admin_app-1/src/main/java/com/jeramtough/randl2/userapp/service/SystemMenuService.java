package com.jeramtough.randl2.userapp.service;

import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.randl2.common.model.dto.SystemMenuDto;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
import com.jeramtough.randl2.common.service.BaseService;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface SystemMenuService extends BaseService<SystemMenu, SystemMenuDto> {

    TreeStructure getCurrentAdminUserSystemMenuTree();

    Map<String, Object> getCurrentAdminUserSystemMenuTreeMap();

}
