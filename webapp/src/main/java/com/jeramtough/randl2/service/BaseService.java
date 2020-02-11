package com.jeramtough.randl2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.dto.PageDto;

/**
 * <pre>
 * Created on 2020/2/11 19:56
 * by @author JeramTough
 * </pre>
 */
public interface BaseService<T,D> extends IService<T> {

    public PageDto<D> getBaseDtoListByPage(QueryByPageParams queryByPageParams);
}
