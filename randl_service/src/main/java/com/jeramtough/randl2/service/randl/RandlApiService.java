package com.jeramtough.randl2.service.randl;

import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.entity.RandlApi;
import com.jeramtough.randl2.common.model.params.api.AddApiParams;
import com.jeramtough.randl2.common.model.params.api.UpdateApiParams;
import com.jeramtough.randl2.service.base.MyBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlApiService extends MyBaseService<RandlApi, RandlApiDto> {

    String addApi(AddApiParams params);

    String delete(Long fid);

    String updateApi(UpdateApiParams params);

    RandlApiDto getApi(Long fid);

    List<RandlApiDto> getAllApi();

    List<RandlApiDto> getApiListByKeyword(String keyword);

    List<RandlApiDto> getAllByAppId(Long appId);
}
