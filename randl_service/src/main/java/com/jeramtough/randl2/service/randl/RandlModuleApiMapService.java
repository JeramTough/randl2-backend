package com.jeramtough.randl2.service.randl;

import com.jeramtough.randl2.common.model.dto.RandlModuleApiMapDto;
import com.jeramtough.randl2.common.model.entity.RandlModuleApiMap;
import com.jeramtough.randl2.common.model.params.module.SetModuleApiMapParams;
import com.jeramtough.randl2.service.base.MyBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-26
 */
public interface RandlModuleApiMapService extends MyBaseService<RandlModuleApiMap, RandlModuleApiMapDto> {

    List<Long> getApiListByAppIdAndModuleId(Long appId, Long moduleId);

    String setModuleApiMap(SetModuleApiMapParams params);
}
