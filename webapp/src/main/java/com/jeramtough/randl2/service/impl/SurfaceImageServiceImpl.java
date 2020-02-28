package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtcomponent.key.util.KeyUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.entity.SurfaceImage;
import com.jeramtough.randl2.dao.mapper.AdminUserMapper;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.SurfaceImageDto;
import com.jeramtough.randl2.service.SurfaceImageService;
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
            throw new ApiResponseException(6000);
        }
        //图片大小不允许超过100kb
        if (file.getSize() > 1024 * 500) {
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

        return "更新当前登录用户头像图片成功！";
    }

    @Override
    public String getUpdateSurfaceImageByUid(Long uid) {
        RegisteredUser registeredUser = registeredUserMapper.selectById(uid);
        if (registeredUser == null) {
            throw new ApiResponseException(6010);
        }
        SurfaceImage surfaceImage = getById(registeredUser.getSurfaceImageId());
        return surfaceImage.getSurfaceImage();
    }

    @Override
    public String getUpdateSurfaceImageById(Long fid) {
        SurfaceImage surfaceImage = getById(fid);
        if (surfaceImage == null) {
            throw new ApiResponseException(6020);
        }
        return surfaceImage.getSurfaceImage();
    }

    @Override
    public String updateSurfaceImageByBase64(UpdateSurfaceImageParams params) {

        RegisteredUser registeredUser = registeredUserMapper.selectById(params.getUid());
        if (registeredUser == null) {
            throw new ApiResponseException(6010);
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
            throw new ApiResponseException(6030);
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
