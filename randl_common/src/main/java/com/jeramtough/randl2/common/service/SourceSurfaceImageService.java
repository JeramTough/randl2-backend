package com.jeramtough.randl2.common.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.common.model.entity.SourceSurfaceImage;
import com.jeramtough.randl2.common.model.dto.SourceSurfaceImageDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
public interface SourceSurfaceImageService extends BaseDtoService<SourceSurfaceImage, SourceSurfaceImageDto> {

    String addUpdateSurfaceImage(MultipartFile file);

    String getUpdateSurfaceImageByUid(Long uid);

    String getUpdateSurfaceImageById(Long fid);

    String updateSurfaceImageByBase64(UpdateSurfaceImageParams params);

    SourceSurfaceImageDto uploadSurfaceImageByBase64(UploadSurfaceImageParams params);

    SourceSurfaceImageDto updateCurrentAdminSurfaceImageByBase64(UpdateCurrentAdminSurfaceImageParams params);
}
