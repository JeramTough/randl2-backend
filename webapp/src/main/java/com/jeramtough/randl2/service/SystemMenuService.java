package com.jeramtough.randl2.service;

import com.jeramtough.randl2.model.dto.SystemMenuDto;
import com.jeramtough.randl2.model.entity.SystemMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface SystemMenuService extends BaseService<SystemMenu, SystemMenuDto> {

    List<SystemMenu> getCurrentAdminUserSystemMenus();

    List<SystemMenu> getCurrentAdminUserSystemMenuDtos();

}
