package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.dao.entity.Api;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.ApiDto;
import com.jeramtough.randl2.dto.PageDto;
import com.jeramtough.randl2.dto.RoleDto;

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
}
