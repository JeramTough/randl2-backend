package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.MenuApiPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-09-14
 */
@Mapper
@Repository
public interface MenuApiPermissionMapper extends BaseMapper<MenuApiPermission> {

}
