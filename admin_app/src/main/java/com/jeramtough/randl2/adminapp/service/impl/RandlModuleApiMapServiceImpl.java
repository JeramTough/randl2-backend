package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.randl2.adminapp.service.RandlModuleApiMapService;
import com.jeramtough.randl2.common.mapper.RandlModuleApiMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleApiMapDto;
import com.jeramtough.randl2.common.model.entity.RandlModuleApiMap;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-26
 */
@Service
public class RandlModuleApiMapServiceImpl extends MyBaseServiceImpl<RandlModuleApiMapMapper, RandlModuleApiMap,
        RandlModuleApiMapDto> implements RandlModuleApiMapService {

    public RandlModuleApiMapServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    protected RandlModuleApiMapDto toDto(RandlModuleApiMap randlModuleApiMap) {
        return null;
    }
}
