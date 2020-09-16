package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.AppApiDto;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import com.jeramtough.randl2.common.model.entity.AppApi;
import com.jeramtough.randl2.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface AppApiService extends BaseService<AppApi, AppApiDto> {

    String addApi(AddApiParams params);

    String delete(Long fid);

    String updateApi(UpdateApiParams params);

    AppApiDto getApi(Long fid);

    List<AppApiDto> getAllApi();

    List<AppApiDto> getApiListByKeyword(String keyword);
}
