package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.key.util.KeyUtil;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.dao.entity.SurfaceImage;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.SurfaceImageDto;
import com.jeramtough.randl2.service.SurfaceImageService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@Service
public class SurfaceImageServiceImpl extends BaseServiceImpl<SurfaceImageMapper,
        SurfaceImage, SurfaceImageDto> implements SurfaceImageService, WithLogger {

    public SurfaceImageServiceImpl(WebApplicationContext wc,
                                   MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected SurfaceImageDto toDto(SurfaceImage surfaceImage) {
        return null;
    }

    @Override
    public String addUpdateSurfaceImage(MultipartFile file) {
//        String suffix = file.getContentType().split("/")[1].equals("png") ? "png" : "jpg";
        if (file.isEmpty()) {
            throw new ApiResponseException(6000);
        }
        //图片大小不允许超过100kb
        if (file.getSize() > 1024 * 100) {
            throw new ApiResponseException(6001);
        }

        boolean isImage = "image/jpeg".equals(file.getContentType()) || "image/png".equals(
                file.getContentType());
        if (!isImage) {
            throw new ApiResponseException(6002);
        }

        try {
            String base64Image = KeyUtil.toBase64Str(file.getBytes());
            SystemUser systemUser = UserHolder.getSystemUser();
            SurfaceImage surfaceImage = getBaseMapper().selectById(
                    systemUser.getSurfaceImageId());
            //系统必须有默认头像
            Objects.requireNonNull(surfaceImage);
            surfaceImage.setSurfaceImage(base64Image);
            updateById(surfaceImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "更新头像图片成功！";
    }
}
