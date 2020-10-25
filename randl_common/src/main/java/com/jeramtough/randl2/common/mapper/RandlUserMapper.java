package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.adminuser.ConditionUserParams;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
public interface RandlUserMapper extends BaseMapper<RandlUser> {

    @Select("SELECT * FROM randl_user WHERE account=#{account}")
    RandlUser selectByAccount(@Param("account") String account);

    @Select("SELECT * FROM randl_user WHERE phone_number=#{phoneNumber}")
    RandlUser selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Select("SELECT * FROM randl_user WHERE email_address=#{emailAddress}")
    RandlUser selectByEmailAddress(@Param("emailAddress") String emailAddress);

    @Select("SELECT * FROM randl_user WHERE  phone_number=#{phoneOrEmail} OR " +
            "email_address=#{phoneOrEmail}")
    RandlUser selectByPhoneNumberOrEmailAddress(String phoneOrEmail);

    @Select("SELECT * FROM randl_user WHERE  phone_number=#{credentials} OR " +
            "email_address=#{credentials} OR account=#{credentials}")
    RandlUser selectByCredentials(String credentials);

    @Update("UPDATE randl_user SET password=#{newPassword} WHERE  " +
            "uid=#{uid};")
    void updatePassword(@Param("uid") Long uid, @Param("newPassword") String newPassword);

    @Update("UPDATE randl_user SET phone_number=#{phoneNumber} WHERE  " +
            "uid=#{uid};")
    void updatePhoneNumber(@Param("uid") Long uid, @Param("phoneNumber") String phoneNumber);

    @Update("UPDATE randl_user SET email_address=#{emailAddress} WHERE  " +
            "uid=#{uid};")
    void updateEmailAddress(@Param("uid") Long uid, @Param("emailAddress") String emailAddress);

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    int insert(RandlUser entity);

    @Update("UPDATE randl_user SET surface_image_id=#{surfaceImageId} WHERE  " +
            "uid=#{uid};")
    void updateSurfaceImageId(@Param("uid") Long uid, @Param("surfaceImageId") Long surfaceImageId);

    @SelectProvider(type = RandlUserSqlProvider.class, method = "selectByCondition")
    QueryPage<RandlUser> selectByCondition(QueryPage<RandlUser> page, @Param("params") ConditionUserParams params);
}
