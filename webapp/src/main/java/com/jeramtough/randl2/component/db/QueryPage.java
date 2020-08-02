package com.jeramtough.randl2.component.db;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeramtough.randl2.model.params.QueryByPageParams;
import io.swagger.annotations.ApiModel;

/**
 * <pre>
 * Created on 2020/2/8 21:13
 * by @author JeramTough
 * </pre>
 */
@ApiModel("分页查询结果对象")
public class QueryPage<T> extends Page<T> {

    public QueryPage(QueryByPageParams params) {
        super(params.getIndex(), params.getSize(),true);
    }
}
