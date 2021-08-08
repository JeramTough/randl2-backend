package com.jeramtough.randl2.service.randl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.mapper.OperationLogMapper;
import com.jeramtough.randl2.common.model.dto.RandlOperationLogDto;
import com.jeramtough.randl2.common.model.entity.RandlOperationLog;
import com.jeramtough.randl2.common.model.params.optlog.ConditionOptionLogParams;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.randl.RandlOperationLogService;
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
public class RandlOperationLogServiceImpl extends MyBaseServiceImpl<OperationLogMapper,
        RandlOperationLog, RandlOperationLogDto> implements RandlOperationLogService {

    @Autowired
    public RandlOperationLogServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    protected RandlOperationLogDto toDto(RandlOperationLog randlOperationLog) {
        return getMapperFacade().map(randlOperationLog, RandlOperationLogDto.class);
    }


    @Override
    public PageDto<RandlOperationLogDto> getBaseDtoListByPage(QueryByPageParams queryByPageParams) {
        BeanValidator.verifyParams(queryByPageParams);
        QueryWrapper<RandlOperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        QueryPage<RandlOperationLog> queryPage = this.getBaseMapper().selectPage(new QueryPage(queryByPageParams),
                queryWrapper);
        return this.toPageDto(queryPage);
    }

    @Override
    public PageDto<RandlOperationLogDto> pageByCondition(QueryByPageParams queryByPageParams,
                                                         ConditionOptionLogParams params) {
        QueryWrapper<RandlOperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        if (params.getAdminIdOrName() != null) {
            queryWrapper.nested(wrapper -> {
                wrapper
                        .eq("admin_id", params.getAdminIdOrName())
                        .or()
                        .eq("admin_name", params.getAdminIdOrName());
            });
        }
        return super.pageByConditionTwo(queryByPageParams, params, queryWrapper);
    }
}
