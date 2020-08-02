package com.jeramtough.randl2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.model.params.QueryByPageParams;
import com.jeramtough.randl2.model.dto.PageDto;

import java.util.List;

/**
 * <pre>
 * Created on 2020/2/11 19:56
 * by @author JeramTough
 * </pre>
 */
public interface BaseService<T,D> extends IService<T> {

     PageDto<D> getBaseDtoListByPage(QueryByPageParams queryByPageParams);

    List<D> getAllBaseDto();

    D getBaseDtoById(Long id);

}
