package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.model.entity.SurfaceImage;
import com.jeramtough.randl2.model.dto.SurfaceImageDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
public interface SurfaceImageService extends BaseService<SurfaceImage, SurfaceImageDto> {

    String addUpdateSurfaceImage(MultipartFile file);

    String getUpdateSurfaceImageByUid(Long uid);

    String getUpdateSurfaceImageById(Long fid);

    String updateSurfaceImageByBase64(UpdateSurfaceImageParams params);

    SurfaceImageDto uploadSurfaceImageByBase64(UploadSurfaceImageParams params);

    SurfaceImageDto updateCurrentAdminSurfaceImageByBase64(UpdateCurrentAdminSurfaceImageParams params);
}
