package com.jeramtough.randl2.common.mapper;

import com.jeramtough.randl2.common.model.entity.SystemApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-09-16
 */
@Mapper
@Repository
public interface SystemApiMapper extends BaseMapper<SystemApi> {

}
