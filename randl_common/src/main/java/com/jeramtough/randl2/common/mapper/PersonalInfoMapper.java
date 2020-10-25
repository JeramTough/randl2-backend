package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.randl2.common.model.entity.RandlPersonalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

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
public interface PersonalInfoMapper extends BaseMapper<RandlPersonalInfo> {

    @Select("SELECT * FROM select_user_personal_info_view")
    QueryPage<Map<String, Object>> selectPageWithUser(QueryPage<Object> objectQueryPage);
}
