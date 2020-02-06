package com.jeramtough.randl2.service;

import com.jeramtough.randl2.dao.entity.SurfaceImage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
public interface SurfaceImageService extends IService<SurfaceImage> {

    String addUpdateSurfaceImage(MultipartFile file);

}
