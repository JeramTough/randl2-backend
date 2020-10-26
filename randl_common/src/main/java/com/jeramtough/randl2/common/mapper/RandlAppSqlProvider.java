package com.jeramtough.randl2.common.mapper;

import com.jeramtough.jtweb.db.mapper.BaseSqlProvider;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * <pre>
 * Created on 2020/10/25 23:56
 * by @author WeiBoWen
 * </pre>
 */
public class RandlAppSqlProvider extends BaseSqlProvider<RandlApp> {

    public String selectByCondition(QueryPage<Object> objectQueryPage, ConditionAppParams params) {
        SQL sql = getCommonSql(RandlApp.class);
        if (params.getKeyword() != null) {
            sql.WHERE(
                    String.format("%s.app_name LIKE %s", getTableAliasName(),
                            "CONCAT('%',#{params.keyword},'%')"));
            sql.OR().WHERE(String.format("%s.app_code = #{params.keyword}", getTableAliasName()));
            sql.OR().WHERE(String.format("%s.description LIKE %s", getTableAliasName(),
                    "CONCAT('%',#{params.keyword},'%')"));
        }

        if (params.getStartDate() != null && params.getEndDate() != null) {
            sql.AND().WHERE(String.format("%s.create_time BETWEEN #{params.startDate} AND #{params.endDate}",
                    getTableAliasName()));
        }

        String sqlStr = sql.toString();
        return sqlStr;
    }

    public String selectAllOnlyName() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT fid, app_name ").append("FROM ")
                  .append(getTableName(RandlApp.class));
        return sqlBuilder.toString();
    }
}
