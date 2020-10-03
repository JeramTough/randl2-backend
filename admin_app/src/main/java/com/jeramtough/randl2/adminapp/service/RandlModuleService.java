package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface RandlModuleService extends BaseService<RandlModule, RandlModuleDto> {

    TreeStructure getCurrentAdminUserSystemMenuTree();

    Map<String, Object> getCurrentAdminUserSystemMenuTreeMap();

}
