package com.jeramtough.randl2.common.mapper;

import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUserWithRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * <pre>
 * Created on 2020/10/3 21:15
 * by @author WeiBoWen
 * </pre>
 */
public class RandlUserRoleMapSqlProvider {


   /* public String selectOneRandlUserByAppIdAndAccount(@Param("appId") Long appId,
                                                      @Param("account") String account) {
        String sql = new SQL() {{
            SELECT("tb_1.uid");
            SELECT("tb_1.account");
            SELECT("tb_1.phone_number");
            SELECT("tb_1.email_address");
            SELECT("tb_1.password");
            SELECT("tb_1.registration_time");
            SELECT("tb_1.registration_ip");
            SELECT("tb_1.account_status");
            SELECT("tb_1.surface_image_id");
            SELECT("tb_1.channel");
            SELECT("tb_2.is_able");
            SELECT("tb_2.role_id");
            SELECT("tb_3.name AS role_name");
            SELECT("tb_3.app_id");
            SELECT("tb_3.alias_name AS role_alias_name");
            FROM("randl_user AS tb_1");
            INNER_JOIN("randl_user_role_map AS tb_2 ON tb_1.uid = tb_2.uid");
            INNER_JOIN("randl_role AS tb_3 ON tb_2.role_id = tb_3.fid");
            WHERE("tb_1.account = #{account}");
            AND();
            WHERE("tb_3.app_id = #{appId}");
        }}.toString();
        return sql;
    }*/

   /* public String selectOneRandlRoleByAppIdAndUid(@Param("appId") Long appId,
                                                  @Param("uid") Long uid) {
        String sql = new SQL() {{
            SELECT("tb_1.fid");
            SELECT("tb_1.name");
            SELECT("tb_1.description");
            SELECT("tb_1.app_id");
            SELECT("tb_1.create_time");
            SELECT("tb_1.alias_name");
            SELECT("randl_user AS tb_1");
            FROM("randl_role AS tb_1");
            INNER_JOIN(" randl_user_role_map AS tb_2 ON tb_1.fid = tb_2.role_id");
            WHERE("tb_1.app_id = #{appId}");
            AND();
            WHERE("tb_2.uid = #{uid}");
        }}.toString();

        return sql;
    }*/

}
