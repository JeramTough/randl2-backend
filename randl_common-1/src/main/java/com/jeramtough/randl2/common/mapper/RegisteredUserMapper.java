package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface RegisteredUserMapper extends BaseMapper<RegisteredUser> {

    @Select("SELECT * FROM registered_user WHERE account=#{account}")
    RegisteredUser selectByAccount(@Param("account") String account);

    @Select("SELECT * FROM registered_user WHERE phone_number=#{phoneNumber}")
    RegisteredUser selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Select("SELECT * FROM registered_user WHERE email_address=#{emailAddress}")
    RegisteredUser selectByEmailAddress(@Param("emailAddress") String emailAddress);

    @Select("SELECT * FROM registered_user WHERE  phone_number=#{phoneOrEmail} OR " +
            "email_address=#{phoneOrEmail}")
    RegisteredUser selectByPhoneNumberOrEmailAddress(String phoneOrEmail);

    @Select("SELECT * FROM registered_user WHERE  phone_number=#{credentials} OR " +
            "email_address=#{credentials} OR account=#{credentials}")
    RegisteredUser selectByCredentials(String credentials);

    @Update("UPDATE registered_user SET password=#{newPassword} WHERE  " +
            "uid=#{uid};")
    void updatePassword(@Param("uid") Long uid, @Param("newPassword") String newPassword);

    @Update("UPDATE registered_user SET phone_number=#{phoneNumber} WHERE  " +
            "uid=#{uid};")
    void updatePhoneNumber(@Param("uid") Long uid, @Param("phoneNumber") String phoneNumber);

    @Update("UPDATE registered_user SET email_address=#{emailAddress} WHERE  " +
            "uid=#{uid};")
    void updateEmailAddress(@Param("uid") Long uid, @Param("emailAddress") String emailAddress);
}
