package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.db.mapper.BaseSqlProvider;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
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
 * @since 2020-08-06
 */
public class OauthClientDetailsSqlProvider extends BaseSqlProvider<OauthClientDetails> {

    public String selectByClientId(@Param("clientId") String clientId) {
        String sql = new StringBuilder()
                .append(" SELECT ")
                .append("tb_1.fid,")
                .append("tb_1.app_id,")
                .append("tb_1.client_id,")
                .append("tb_1.resource_ids,")
                .append("tb_1.client_secret,")
                .append("tb_1.authorized_grant_types,")
                .append("tb_1.web_server_redirect_uris,")
                .append("tb_1.authorities,")
                .append("tb_1.access_token_validity,")
                .append("tb_1.refresh_token_validity,")
                .append("tb_1.auto_approve,")
                .append("tb_2.app_name,")
                .append("tb_2.description,")
                .append("tb_2.is_able,")
                .append("tb_2.create_time,")
                .append("tb_2.type")
                .append(" FROM ")
                .append(" oauth_client_details AS tb_1 ")
                .append(" INNER JOIN randl_app AS tb_2 ON tb_1.app_id = tb_2.fid ")
                .append(" WHERE tb_1.client_id=#{clientId}").toString();
        return sql;
    }
}
