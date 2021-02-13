package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Mapper
@Repository
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    @SelectProvider(type = OauthClientDetailsSqlProvider.class, method = "selectByClientId")
    OauthClientDetailsDto selectByClientId(@Param("clientId") String clientId);
}
