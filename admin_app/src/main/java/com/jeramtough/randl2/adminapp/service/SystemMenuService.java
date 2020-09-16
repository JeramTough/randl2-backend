package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.randl2.common.model.dto.SystemMenuDto;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
import com.jeramtough.randl2.common.service.BaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface SystemMenuService extends BaseService<SystemMenu, SystemMenuDto> {

    TreeStructure getCurrentAdminUserSystemMenuTrees();

}
