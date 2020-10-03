package com.jeramtough.randl2.userapp.service.impl;

import com.jeramtough.jtcomponent.utils.Base64Util;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.model.entity.SurfaceImage;
import com.jeramtough.randl2.common.mapper.AdminUserMapper;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.common.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.SurfaceImageDto;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.service.SurfaceImageService;
import ma.glasnost.orika.MapperFacade;
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
public class SurfaceImageServiceImpl extends BaseServiceImpl<SurfaceImageMapper,
        SurfaceImage, SurfaceImageDto> implements SurfaceImageService, WithLogger {

    private RegisteredUserMapper registeredUserMapper;
    private AdminUserMapper adminUserMapper;

    @Autowired
    public SurfaceImageServiceImpl(WebApplicationContext wc,
                                   MapperFacade mapperFacade,
                                   RegisteredUserMapper registeredUserMapper,
                                   AdminUserMapper adminUserMapper) {
        super(wc, mapperFacade);
        this.registeredUserMapper = registeredUserMapper;
        this.adminUserMapper = adminUserMapper;
    }

    @Override
    protected SurfaceImageDto toDto(SurfaceImage surfaceImage) {
        return getMapperFacade().map(surfaceImage, SurfaceImageDto.class);
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

        return "更新当前登录用户头像图片成功！";
    }

    @Override
    public String getUpdateSurfaceImageByUid(Long uid) {
        RegisteredUser registeredUser = registeredUserMapper.selectById(uid);
        if (registeredUser == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "用户");
        }
        SurfaceImage surfaceImage = getById(registeredUser.getSurfaceImageId());
        return surfaceImage.getSurfaceImage();
    }

    @Override
    public String getUpdateSurfaceImageById(Long fid) {
        SurfaceImage surfaceImage = getById(fid);
        if (surfaceImage == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "图片");
        }
        return surfaceImage.getSurfaceImage();
    }

    @Override
    public String updateSurfaceImageByBase64(UpdateSurfaceImageParams params) {

        RegisteredUser registeredUser = registeredUserMapper.selectById(params.getUid());
        if (registeredUser == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "用户");
        }

        UploadSurfaceImageParams uploadParams = new UploadSurfaceImageParams();
        uploadParams.setSurfaceImage(params.getSurfaceImage());
        SurfaceImageDto surfaceImageDto = uploadSurfaceImageByBase64(uploadParams);

        registeredUser.setSurfaceImageId(surfaceImageDto.getFid());
        registeredUserMapper.updateById(registeredUser);
        return "更新用户头像成功";
    }

    @Override
    public SurfaceImageDto uploadSurfaceImageByBase64(UploadSurfaceImageParams params) {
        SurfaceImage surfaceImage = getMapperFacade().map(params,
                SurfaceImage.class);
        getBaseMapper().insertSurfaceImage(surfaceImage);
        SurfaceImageDto surfaceImageDto = getMapperFacade().map(surfaceImage,
                SurfaceImageDto.class);
        return surfaceImageDto;
    }

    @Override
    public SurfaceImageDto updateCurrentAdminSurfaceImageByBase64(
            UpdateCurrentAdminSurfaceImageParams params) {

        BeanValidator.verifyDto(params);

        SystemUser systemUser = UserHolder.getSystemUser();
        if (UserHolder.isSuperAdmin()) {
            throw new ApiResponseException(ErrorU.CODE_604.C);
        }

        SurfaceImage surfaceImage = getMapperFacade().map(params,
                SurfaceImage.class);
        getBaseMapper().insertSurfaceImage(surfaceImage);
        SurfaceImageDto surfaceImageDto = getMapperFacade().map(surfaceImage,
                SurfaceImageDto.class);

        systemUser.setSurfaceImageId(surfaceImage.getFid());
        adminUserMapper.updateSurfaceImageId(systemUser.getUid(), surfaceImage.getFid());

        return surfaceImageDto;
    }

}
