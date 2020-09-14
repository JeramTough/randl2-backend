package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.randl2.common.model.dto.SystemMenuDto;
import com.jeramtough.randl2.common.model.entity.SystemMenu;

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

    TreeNode getCurrentAdminUserSystemMenuTree();

}
