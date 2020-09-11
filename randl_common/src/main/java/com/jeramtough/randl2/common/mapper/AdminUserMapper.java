package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.AdminUser;
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
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    @Select("SELECT * FROM admin_user WHERE username = #{keyword}" +
            " OR phone_number = #{keyword}" +
            " OR email_address = #{keyword}")
    AdminUser selectByKeyword(@Param("keyword") String keyword);

    @Update("UPDATE admin_user SET surface_image_id=#{surfaceImageId} WHERE uid=#{uid}")
    void updateSurfaceImageId(@Param("uid") long uid,
                            @Param("surfaceImageId") long surfaceImageId);

}
