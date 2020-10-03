package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.entity.RandlApi;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import com.jeramtough.randl2.common.mapper.RandlApiMapper;
import com.jeramtough.randl2.adminapp.service.RandlApiService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RandlApiServiceImpl extends BaseServiceImpl<RandlApiMapper, RandlApi, RandlApiDto>
        implements RandlApiService,
        WithLogger {


    public RandlApiServiceImpl(WebApplicationContext wc,
                               MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected RandlApiDto toDto(RandlApi randlApi) {
        return getMapperFacade().map(randlApi, RandlApiDto.class);
    }

    @Override
    public String addApi(AddApiParams params) {
        BeanValidator.verifyParams(params);
        params.setDescription(params.getDescription().trim());
        params.setAlias(params.getAlias().trim());

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlApi>().eq("path", params.getPath())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口路径");
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlApi>().eq("alias", params.getAlias())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口别名");
        }

        RandlApi randlApi = getMapperFacade().map(params, RandlApi.class);
        save(randlApi);
        return "添加API接口信息成功";
    }

    @Override
    public String delete(Long fid) {
        RandlApi randlApi = getBaseMapper().selectById(fid);
        if (randlApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        getBaseMapper().deleteById(fid);
        return "删除接口【" + randlApi.getPath() + "】成功";
    }

    @Override
    public String updateApi(UpdateApiParams params) {
        BeanValidator.verifyParams(params);
        if (params.getDescription() != null) {
            params.setDescription(params.getDescription().trim());
        }
        if (params.getAlias() != null) {
            params.setAlias(params.getAlias().trim());
        }

        RandlApi randlApi = getBaseMapper().selectById(params.getFid());
        if (randlApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        if (!randlApi.getPath().equals(params.getPath())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlApi>().eq("path", params.getPath())) != null) {
                throw new ApiResponseException(ErrorU.CODE_9.C, "接口路径");
            }
        }
        if (!randlApi.getAlias().equals(params.getAlias())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlApi>().eq("alias", params.getAlias())) != null) {
                throw new ApiResponseException(ErrorU.CODE_9.C, "接口别名");
            }
        }
        randlApi = getMapperFacade().map(params, RandlApi.class);
        updateById(randlApi);
        return "更新接口【" + randlApi.getPath() + "】成功";
    }

    @Override
    public RandlApiDto getApi(Long fid) {
        RandlApi randlApi = getBaseMapper().selectById(fid);
        if (randlApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        return getBaseDto(randlApi);
    }

    @Override
    public List<RandlApiDto> getAllApi() {
        List<RandlApiDto> randlApiDtoList = getMapperFacade().mapAsList(list(), RandlApiDto.class);
        return randlApiDtoList;
    }

    @Override
    public List<RandlApiDto> getApiListByKeyword(String keyword) {
        QueryWrapper<RandlApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("path", "%" + keyword + "%")
                    .or().like("description", "%" + keyword + "%");
        List<RandlApi> randlApiList = getBaseMapper().selectList(queryWrapper);
        if (randlApiList == null) {
            throw new ApiResponseException(ErrorU.CODE_8.C, "接口");
        }
        return getDtoList(randlApiList);
    }


}
