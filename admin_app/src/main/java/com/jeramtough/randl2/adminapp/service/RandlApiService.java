package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.entity.RandlApi;
import com.jeramtough.randl2.common.model.params.api.ConditionApiParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlApiService extends BaseDtoService<RandlApi, RandlApiDto> {

    String addApi(AddApiParams params);

    String delete(Long fid);

    String updateApi(UpdateApiParams params);

    RandlApiDto getApi(Long fid);

    List<RandlApiDto> getAllApi();

    List<RandlApiDto> getApiListByKeyword(String keyword);

    PageDto<RandlApiDto> pageByCondition(QueryByPageParams queryByPageParams, ConditionApiParams params);
}
