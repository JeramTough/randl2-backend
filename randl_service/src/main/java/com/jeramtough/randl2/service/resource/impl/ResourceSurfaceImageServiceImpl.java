package com.jeramtough.randl2.service.resource.impl;

import com.jeramtough.jtcomponent.utils.Base64Util;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.SourceSurfaceImageDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.entity.SourceSurfaceImage;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.service.resource.ResourceSurfaceImageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResourceSurfaceImageServiceImpl extends BaseDtoServiceImpl<SourceSurfaceImageMapper,
        SourceSurfaceImage, SourceSurfaceImageDto> implements ResourceSurfaceImageService, WithLogger {

    private RandlUserMapper randlUserMapper;

    @Autowired
    public ResourceSurfaceImageServiceImpl(WebApplicationContext wc,
                                           RandlUserMapper randlUserMapper) {
        super(wc);
        this.randlUserMapper = randlUserMapper;
    }

    @Override
    protected SourceSurfaceImageDto toDto(SourceSurfaceImage sourceSurfaceImage) {
        return getMapperFacade().map(sourceSurfaceImage, SourceSurfaceImageDto.class);
    }

    @Override
    public String addUpdateSurfaceImage(MultipartFile file) {
//        String suffix = file.getContentType().split("/")[1].equals("png") ? "png" : "jpg";
        if (file.isEmpty()) {
            throw new ApiResponseException(ErrorU.CODE_601.C);
        }
        //图片大小不允许超过500kb
        if (file.getSize() > 1024 * 500) {
            throw new ApiResponseException(ErrorU.CODE_602.C, "500kb");
        }

        boolean isImage = "image/jpeg".equals(file.getContentType()) || "image/png".equals(
                file.getContentType());
        if (!isImage) {
            throw new ApiResponseException(ErrorU.CODE_603.C, "jpg或png");
        }

        try {
            String base64Image = Base64Util.toBase64Str(file.getBytes());
            SystemUser systemUser = UserHolder.getSystemUser();
            SourceSurfaceImage sourceSurfaceImage = getBaseMapper().selectById(
                    systemUser.getSurfaceImageId());
            //系统必须有默认头像
            Objects.requireNonNull(sourceSurfaceImage);
            sourceSurfaceImage.setSurfaceImage(base64Image);
            updateById(sourceSurfaceImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "更新当前登录用户头像图片成功！";
    }

    @Override
    public String getUpdateSurfaceImageByUid(Long uid) {
        RandlUser randlUser = randlUserMapper.selectById(uid);
        if (randlUser == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "用户");
        }
        SourceSurfaceImage sourceSurfaceImage = getById(randlUser.getSurfaceImageId());
        return sourceSurfaceImage.getSurfaceImage();
    }

    @Override
    public String getUpdateSurfaceImageById(Long fid) {
        SourceSurfaceImage sourceSurfaceImage = getById(fid);
        if (sourceSurfaceImage == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "图片");
        }
        return sourceSurfaceImage.getSurfaceImage();
    }

    @Override
    public String updateSurfaceImageByBase64(UpdateSurfaceImageParams params) {

        RandlUser randlUser = randlUserMapper.selectById(params.getUid());
        if (randlUser == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "用户");
        }

        UploadSurfaceImageParams uploadParams = new UploadSurfaceImageParams();
        uploadParams.setSurfaceImage(params.getSurfaceImage());
        SourceSurfaceImageDto sourceSurfaceImageDto = uploadSurfaceImageByBase64(uploadParams);

        randlUser.setSurfaceImageId(sourceSurfaceImageDto.getFid());
        randlUserMapper.updateById(randlUser);
        return "更新用户头像成功";
    }

    @Override
    public SourceSurfaceImageDto uploadSurfaceImageByBase64(UploadSurfaceImageParams params) {
        SourceSurfaceImage sourceSurfaceImage = getMapperFacade().map(params,
                SourceSurfaceImage.class);
        getBaseMapper().insertSurfaceImage(sourceSurfaceImage);
        SourceSurfaceImageDto sourceSurfaceImageDto = getMapperFacade().map(sourceSurfaceImage,
                SourceSurfaceImageDto.class);
        return sourceSurfaceImageDto;
    }

    @Override
    public SourceSurfaceImageDto updateCurrentAdminSurfaceImageByBase64(
            UpdateCurrentAdminSurfaceImageParams params) {

        BeanValidator.verifyParams(params);

        SystemUser systemUser = UserHolder.getSystemUser();
        if (UserHolder.isSuperAdmin()) {
            throw new ApiResponseException(ErrorU.CODE_604.C);
        }

        SourceSurfaceImage sourceSurfaceImage = getMapperFacade().map(params,
                SourceSurfaceImage.class);
        getBaseMapper().insertSurfaceImage(sourceSurfaceImage);
        SourceSurfaceImageDto sourceSurfaceImageDto = getMapperFacade().map(sourceSurfaceImage,
                SourceSurfaceImageDto.class);

        systemUser.setSurfaceImageId(sourceSurfaceImage.getFid());
        randlUserMapper.updateSurfaceImageId(systemUser.getUid(), sourceSurfaceImage.getFid());

        return sourceSurfaceImageDto;
    }

}
