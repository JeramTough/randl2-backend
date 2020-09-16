package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.randl2.common.model.dto.OperationLogDto;
import com.jeramtough.randl2.common.model.entity.OperationLog;
import com.jeramtough.randl2.common.mapper.OperationLogMapper;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.service.OperationLogService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Service
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper,
        OperationLog, OperationLogDto> implements OperationLogService {

    @Autowired
    public OperationLogServiceImpl(WebApplicationContext wc,
                                   MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected OperationLogDto toDto(OperationLog operationLog) {
        return getMapperFacade().map(operationLog, OperationLogDto.class);
    }
}
