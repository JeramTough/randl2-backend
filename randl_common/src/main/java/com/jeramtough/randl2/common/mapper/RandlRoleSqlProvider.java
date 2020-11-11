package com.jeramtough.randl2.common.mapper;

import com.jeramtough.jtweb.db.mapper.BaseSqlProvider;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <pre>
 * Created on 2020/11/11 9:06
 * by @author WeiBoWen
 * </pre>
 */
public class RandlRoleSqlProvider extends BaseSqlProvider<RandlRole> {


    public String selectListByUid(@Param("uid") Long uid,
                                                 @Param("appId") Long appId) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ")
                .append("tb_1.uid,")
                .append("tb_1.role_id,")
                .append("tb_2.name,")
                .append("tb_2.description,")
                .append("tb_2.app_id,")
                .append("tb_2.create_time,")
                .append("tb_2.alias,")
                .append("tb_2.fid")
                .append(" FROM ")
                .append(" randl_user_role_map AS tb_1 ")
                .append(" RIGHT JOIN randl_role AS tb_2 ON tb_1.role_id = tb_2.fid ")
                .append(" WHERE (tb_1.uid=#{uid}) AND (tb_2.app_id=#{appId})");
        return sqlBuilder.toString();
    }


}
