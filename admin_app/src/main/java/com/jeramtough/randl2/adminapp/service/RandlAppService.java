package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;

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
public interface RandlAppService extends BaseDtoService<RandlApp, RandlAppDto> {

    String add(AddAppParams params);

    String removeOneById(Serializable id);

    String update(UpdateAppParams params);

    List<RandlAppDto> getListByKeyword(String keyword);
}
