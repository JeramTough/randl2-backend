package com.jeramtough.randl2.service.impl;

import com.jeramtough.randl2.model.dto.LogOperationDto;
import com.jeramtough.randl2.model.entity.LogOperation;
import com.jeramtough.randl2.dao.mapper.LogOperationMapper;
import com.jeramtough.randl2.service.LogOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class LogOperationServiceImpl extends BaseServiceImpl<LogOperationMapper,
        LogOperation, LogOperationDto> implements LogOperationService {

    @Autowired
    public LogOperationServiceImpl(WebApplicationContext wc,
                                   MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected LogOperationDto toDto(LogOperation logOperation) {
        return getMapperFacade().map(logOperation, LogOperationDto.class);
    }
}
