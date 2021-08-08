package com.jeramtough.randl2.service.randl;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlOperationLogDto;
import com.jeramtough.randl2.common.model.entity.RandlOperationLog;
import com.jeramtough.randl2.common.model.params.optlog.ConditionOptionLogParams;
import com.jeramtough.randl2.service.base.MyBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface RandlOperationLogService extends MyBaseService<RandlOperationLog, RandlOperationLogDto> {


    PageDto<RandlOperationLogDto> pageByCondition(QueryByPageParams queryByPageParams, ConditionOptionLogParams params);
}
