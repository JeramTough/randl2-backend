package com.jeramtough.randl2.dao.mapper;

import com.jeramtough.randl2.model.entity.SurfaceImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@Mapper
@Repository
public interface SurfaceImageMapper extends BaseMapper<SurfaceImage> {


    @Insert("INSERT INTO surface_image VALUES(null,#{surfaceImage})")
    @Options(useGeneratedKeys = true, keyProperty = "fid", keyColumn = "fid")
    int insertSurfaceImage(SurfaceImage surfaceImage);


}
