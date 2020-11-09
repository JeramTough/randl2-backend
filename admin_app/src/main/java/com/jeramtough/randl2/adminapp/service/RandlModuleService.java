package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.params.module.AddRandlModuleParams;
import com.jeramtough.randl2.common.model.params.module.TreeModuleParams;
import com.jeramtough.randl2.common.model.params.module.UpdateModuleParams;
import com.jeramtough.randl2.common.service.MyBaseService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface RandlModuleService extends MyBaseService<RandlModule, RandlModuleDto> {


    Map<String, Object> getRandlModuleTreeMap(Long appId,Long uid);


    TreeNode getTreeModule(TreeModuleParams params);

    Map<String, Object> getTreeModuleList(TreeModuleParams params);

    String add(AddRandlModuleParams params);

    String update(UpdateModuleParams params);

    String removeChainById(Long fid);

}
