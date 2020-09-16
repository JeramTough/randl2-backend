package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
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
 * @since 2020-08-06
 */
@Mapper
@Repository
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    @Select("SELECT " +
            "tb1.fid AS fid," +
            "name," +
            "description," +
            "path," +
            "level," +
            "menu_order," +
            "icon," +
            "parent_id, " +
            "is_able, " +
            "FROM " +
            "system_menu AS tb1 " +
            "RIGHT JOIN menu_role_permission AS tb2 " +
            "ON tb1.fid = tb2.menu_id " +
            "WHERE " +
            "role_id =#{roleId} " +
            "ORDER BY tb1.level")
    List<SystemMenu> selectListByRoleId(@Param("roleId") Long roleId);


}
