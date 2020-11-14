package com.jeramtough.randl2.service.base;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;

/**
 * <pre>
 * Created on 2020/10/25 16:47
 * by @author WeiBoWen
 * </pre>
 */
public interface MyBaseService<T, D> extends BaseDtoService<T, D> {

    String updateByParams(Object params);

    String removeOneById(Long fid);

    PageDto<D> pageByCondition(QueryByPageParams queryByPageParams, BaseConditionParams params);
}
