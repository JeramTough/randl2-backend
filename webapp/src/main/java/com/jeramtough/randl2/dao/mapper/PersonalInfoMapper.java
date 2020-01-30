package com.jeramtough.randl2.dao.mapper;

import com.jeramtough.randl2.dao.entity.PersonalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface PersonalInfoMapper extends BaseMapper<PersonalInfo> {

}
