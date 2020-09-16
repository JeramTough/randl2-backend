package com.jeramtough.randl2.userapp.service;

import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.common.model.entity.SurfaceImage;
import com.jeramtough.randl2.common.model.dto.SurfaceImageDto;
import com.jeramtough.randl2.common.service.BaseService;
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
