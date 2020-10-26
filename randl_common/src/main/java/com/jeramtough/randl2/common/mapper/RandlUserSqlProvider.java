package com.jeramtough.randl2.common.mapper;

import com.jeramtough.jtweb.db.mapper.BaseSqlProvider;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * <pre>
 * Created on 2020/10/20 14:00
 * by @author WeiBoWen
 * </pre>
 */
public class RandlUserSqlProvider extends BaseSqlProvider<RandlUser> {

    public String selectByCondition(QueryPage<RandlUser> page, @Param("params") ConditionUserParams params) {
        SQL sql = getCommonSql(RandlUser.class);
        if (params.getKeyword() != null) {
            sql.WHERE(
                    String.format("%s.account LIKE %s", getTableAliasName(), "CONCAT('%',#{params.keyword},'%')"));
            sql.OR().WHERE(String.format("%s.phone_number = #{params.keyword}", getTableAliasName()));
            sql.OR().WHERE(String.format("%s.email_address = #{params.keyword}", getTableAliasName()));
        }

        if (params.getStartDate() != null && params.getEndDate() != null) {
            sql.AND().WHERE(String.format("%s.registration_time BETWEEN #{params.startDate} AND #{params.endDate}",
                    getTableAliasName()));
        }

        String sqlStr = sql.toString();
        return sqlStr;
    }
}
