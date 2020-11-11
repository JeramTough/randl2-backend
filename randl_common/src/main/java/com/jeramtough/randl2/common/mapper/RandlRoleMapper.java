package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Mapper
@Repository
public interface RandlRoleMapper extends BaseMapper<RandlRole> {


    @SelectProvider(type = RandlRoleSqlProvider.class, method = "selectListByUid")
    List<RandlRole> selectListByUid(@Param("uid") Long uid,@Param("appId") Long appId);


}
