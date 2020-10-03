package com.jeramtough.randl2.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.randl2.common.model.entity.SourceSurfaceImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
public interface SourceSurfaceImageMapper extends BaseMapper<SourceSurfaceImage> {


    @Insert("INSERT INTO surface_image VALUES(null,#{sourceSurfaceImage})")
    @Options(useGeneratedKeys = true, keyProperty = "fid", keyColumn = "fid")
    int insertSurfaceImage(SourceSurfaceImage sourceSurfaceImage);


}
