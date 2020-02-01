package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.dao.entity.Api;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.ApiDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface ApiService extends IService<Api> {

    String addApi(AddApiParams params);

    String delete(Long apiId);

    String updateApi(UpdateApiParams params);

    ApiDto getApi(Long apiId);

    List<ApiDto> getAllApi();
}
