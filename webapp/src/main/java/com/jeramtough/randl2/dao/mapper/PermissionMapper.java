package com.jeramtough.randl2.dao.mapper;

import com.jeramtough.randl2.dao.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.dto.PermissionDto;
import org.apache.ibatis.annotations.Mapper;
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

}
