package com.jeramtough.randl2.common.mapper;

import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-09-16
 */
public interface RandlAppMapper extends BaseMapper<RandlApp> {

    @SelectProvider(type = RandlAppSqlProvider.class, method = "selectByCondition")
    QueryPage<RandlApp> selectByCondition(QueryPage<Object> objectQueryPage,
                                          @Param("params") ConditionAppParams params);
}
