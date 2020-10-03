package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.dto.PermissionDto;
import com.jeramtough.randl2.common.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT * FROM permission_view")
    List<PermissionDto> selectListPermissionDto();

    @Select("SELECT * FROM permission_view where role_id=#{roleId}")
    List<PermissionDto> selectListPermissionDtoByRoleId(@Param("roleId") Long roleId);

}
