package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.entity.PersonalInfo;
import com.jeramtough.randl2.common.mapper.PersonalInfoMapper;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.common.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.common.model.dto.SurfaceImageDto;
import com.jeramtough.randl2.adminapp.service.PersonalInfoService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class PersonalInfoServiceImpl extends BaseServiceImpl<PersonalInfoMapper,
        PersonalInfo, PersonalInfoDto> implements
        PersonalInfoService {

    private SurfaceImageMapper surfaceImageMapper;
    private RegisteredUserMapper registeredUserMapper;

    @Autowired
    public PersonalInfoServiceImpl(WebApplicationContext wc,
                                   MapperFacade mapperFacade,
                                   SurfaceImageMapper surfaceImageMapper,
                                   RegisteredUserMapper registeredUserMapper) {
        super(wc, mapperFacade);
        this.surfaceImageMapper = surfaceImageMapper;
        this.registeredUserMapper = registeredUserMapper;
    }

    @Override
    protected PersonalInfoDto toDto(PersonalInfo personalInfo) {
        PersonalInfoDto dto = getMapperFacade().map(personalInfo, PersonalInfoDto.class);
        Long surfaceImageId = registeredUserMapper.selectById(
                dto.getUid()).getSurfaceImageId();
        SurfaceImageDto surfaceImageDto =
                getMapperFacade().map(surfaceImageMapper.selectById(surfaceImageId),
                        SurfaceImageDto.class);
        dto.setSurfaceImage(surfaceImageDto);
        return dto;
    }


    @Override
    public PersonalInfoDto getPersonalInfoByUid(Long uid) {
        PersonalInfoDto dto = getPersonalInfoDtoByUidWithoutSurfaceImage(uid);
        Long surfaceImageId = registeredUserMapper.selectById(
                dto.getUid()).getSurfaceImageId();
        SurfaceImageDto surfaceImageDto =
                getMapperFacade().map(surfaceImageMapper.selectById(surfaceImageId),
                        SurfaceImageDto.class);
        dto.setSurfaceImage(surfaceImageDto);
        return dto;
    }

    @Override
    public PersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid) {
        if (registeredUserMapper.selectById(uid) == null) {
            throw new ApiResponseException(669);
        }

        PersonalInfo personalInfo =
                getBaseMapper().selectOne(new QueryWrapper<PersonalInfo>().eq("uid", uid));
        //如果并没有更新过个人资料是没有数据的，需要一个默认的
        if (personalInfo == null) {
            personalInfo = new PersonalInfo();
            personalInfo.setUid(uid);
            getBaseMapper().insert(personalInfo);
            personalInfo = getBaseMapper().selectOne(
                    new QueryWrapper<PersonalInfo>().eq("uid", uid));
        }

        PersonalInfoDto dto = getMapperFacade().map(personalInfo, PersonalInfoDto.class);
        return dto;
    }

    @Override
    public String updatePersonalInfo(UpdatePersonalInfoParams params) {
        BeanValidator.verifyDto(params);

        if (params.getFid() == null && params.getUid() == null) {
            throw new ApiResponseException(ErrorU.CODE_102.C);
        }
        long fid = params.getFid();

        //优先使用uid进行查询
        if (params.getUid() != null) {
            PersonalInfo personalInfo =
                    getBaseMapper().selectOne(new QueryWrapper<PersonalInfo>().eq("uid",
                            params.getUid()));
            if (personalInfo != null) {
                fid = personalInfo.getFid();
            }
        }

        PersonalInfo personalInfo = getMapperFacade().map(params, PersonalInfo.class);
        personalInfo.setFid(fid);

        updateById(personalInfo);
        return "更新普通用户个人资料成功";
    }
}
