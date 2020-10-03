package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.MenuApiPermission;
import com.jeramtough.randl2.common.model.entity.MenuRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-09-14
 */
@Mapper
@Repository
public interface MenuRolePermissionMapper extends BaseMapper<MenuRolePermission> {

    @Select("SELECT *  FROM menu_role_permission WHERE menuId = #{menuId}")
    List<MenuRolePermission> selectListByMenuId(@Param("menuId") Long menuId);

    @Select("SELECT role_id  FROM menu_role_permission WHERE menu_id = #{menuId}")
    List<Long> selectRoleIdsByMenuId(@Param("menuId") Long menuId);

}
