package com.jeramtough.randl2.service.randl;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.service.base.MyBaseService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public interface RandlAppService extends MyBaseService<RandlApp, RandlAppDto> {

    String add(AddAppParams params);

    String update(UpdateAppParams params);

    List<RandlAppDto> getListByKeyword(String keyword);

    PageDto<RandlAppDto> pageByCondition(QueryByPageParams queryByPageParams, ConditionAppParams params);

    List<RandlAppDto> getAllOnlyName();
}
