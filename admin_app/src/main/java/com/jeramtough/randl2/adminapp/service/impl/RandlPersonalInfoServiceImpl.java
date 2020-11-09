package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.model.entity.RandlPersonalInfo;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.mapper.PersonalInfoMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.dto.SourceSurfaceImageDto;
import com.jeramtough.randl2.adminapp.service.RandlPersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RandlPersonalInfoServiceImpl extends BaseDtoServiceImpl<PersonalInfoMapper,
        RandlPersonalInfo, RandlPersonalInfoDto> implements
        RandlPersonalInfoService {

    private SourceSurfaceImageMapper sourceSurfaceImageMapper;
    private RandlUserMapper randlUserMapper;

    @Autowired
    public RandlPersonalInfoServiceImpl(WebApplicationContext wc,
                                        SourceSurfaceImageMapper sourceSurfaceImageMapper,
                                        RandlUserMapper randlUserMapper) {
        super(wc);
        this.sourceSurfaceImageMapper = sourceSurfaceImageMapper;
        this.randlUserMapper = randlUserMapper;
    }

    @Override
    protected RandlPersonalInfoDto toDto(RandlPersonalInfo randlPersonalInfo) {
        RandlPersonalInfoDto dto = getMapperFacade().map(randlPersonalInfo, RandlPersonalInfoDto.class);
        Long surfaceImageId = randlUserMapper.selectById(
                dto.getUid()).getSurfaceImageId();
        SourceSurfaceImageDto sourceSurfaceImageDto =
                getMapperFacade().map(sourceSurfaceImageMapper.selectById(surfaceImageId),
                        SourceSurfaceImageDto.class);
        dto.setSurfaceImage(sourceSurfaceImageDto);
        return dto;
    }


    @Override
    public RandlPersonalInfoDto getPersonalInfoByUid(Long uid) {
        RandlPersonalInfoDto dto = getPersonalInfoDtoByUidWithoutSurfaceImage(uid);
        Long surfaceImageId = randlUserMapper.selectById(
                dto.getUid()).getSurfaceImageId();
        SourceSurfaceImageDto sourceSurfaceImageDto =
                getMapperFacade().map(sourceSurfaceImageMapper.selectById(surfaceImageId),
                        SourceSurfaceImageDto.class);
        dto.setSurfaceImage(sourceSurfaceImageDto);
        return dto;
    }

    @Override
    public RandlPersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid) {
        if (randlUserMapper.selectById(uid) == null) {
            throw new ApiResponseException(ErrorU.CODE_12.C, "用户信息");
        }

        RandlPersonalInfo randlPersonalInfo =
                getBaseMapper().selectOne(new QueryWrapper<RandlPersonalInfo>().eq("uid", uid));
        //如果并没有更新过个人资料是没有数据的，需要一个默认的
        if (randlPersonalInfo == null) {
            randlPersonalInfo = new RandlPersonalInfo();
            randlPersonalInfo.setUid(uid);
            getBaseMapper().insert(randlPersonalInfo);
            randlPersonalInfo = getBaseMapper().selectOne(
                    new QueryWrapper<RandlPersonalInfo>().eq("uid", uid));
        }

        RandlPersonalInfoDto dto = getMapperFacade().map(randlPersonalInfo, RandlPersonalInfoDto.class);
        return dto;
    }

    @Override
    public String updatePersonalInfo(UpdatePersonalInfoParams params) {
        BeanValidator.verifyParams(params);

        if (params.getFid() == null && params.getUid() == null) {
            throw new ApiResponseException(ErrorU.CODE_102.C);
        }
        long fid = params.getFid();

        //优先使用uid进行查询
        if (params.getUid() != null) {
            RandlPersonalInfo randlPersonalInfo =
                    getBaseMapper().selectOne(new QueryWrapper<RandlPersonalInfo>().eq("uid",
                            params.getUid()));
            if (randlPersonalInfo != null) {
                fid = randlPersonalInfo.getFid();
            }
            else {
                throw new ApiResponseException(ErrorU.CODE_12.C);
            }
        }
        else {
            if (getBaseMapper().selectById(fid) == null) {
                throw new ApiResponseException(ErrorU.CODE_9.C, "主键fid");
            }
        }

        RandlPersonalInfo randlPersonalInfo = getMapperFacade().map(params, RandlPersonalInfo.class);
        randlPersonalInfo.setFid(fid);

        updateById(randlPersonalInfo);
        return "更新普通用户个人资料成功";
    }

    @Override
    public PageDto<Map<String, Object>> getRandlPersonalInfoDtoListByPage(QueryByPageParams queryByPageParams) {
        QueryPage<Map<String, Object>> queryPage =
                getBaseMapper().selectPageWithUser(new QueryPage<>(queryByPageParams));
        PageDto<Map<String, Object>> pageDto = toMapPageDto(queryPage);
        return pageDto;
    }


}
