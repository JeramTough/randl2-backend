package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import com.jeramtough.randl2.common.model.entity.Api;
import com.jeramtough.randl2.common.model.dto.ApiDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface ApiService extends BaseService<Api, ApiDto> {

    String addApi(AddApiParams params);

    String delete(Long fid);

    String updateApi(UpdateApiParams params);

    ApiDto getApi(Long fid);

    List<ApiDto> getAllApi();

    List<ApiDto> getApiListByKeyword(String keyword);
}
