package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.RandlUserRoleMap;
import com.jeramtough.randl2.common.model.entity.RandlUserWithRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@Mapper
@Repository
public interface RandlUserRoleMapMapper extends BaseMapper<RandlUserRoleMap> {


    @SelectProvider(type = RandlUserRoleMapSqlProvider.class, method = "selectOneRandlUserByAppIdAndAccount")
    RandlUserWithRole selectOneRandlUserByAppIdAndAccount(@Param("appId") Long appId,
                                                          @Param("account") String account);

}
