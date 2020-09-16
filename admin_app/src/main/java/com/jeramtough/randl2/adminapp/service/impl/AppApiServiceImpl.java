package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.dto.AppApiDto;
import com.jeramtough.randl2.common.model.entity.AppApi;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import com.jeramtough.randl2.common.mapper.AppApiMapper;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.service.AppApiService;
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
public class AppApiServiceImpl extends BaseServiceImpl<AppApiMapper, AppApi, AppApiDto>
        implements AppApiService,
        WithLogger {


    public AppApiServiceImpl(WebApplicationContext wc,
                             MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected AppApiDto toDto(AppApi appApi) {
        return getMapperFacade().map(appApi, AppApiDto.class);
    }

    @Override
    public String addApi(AddApiParams params) {
        BeanValidator.verifyDto(params);
        params.setDescription(params.getDescription().trim());
        params.setAlias(params.getAlias().trim());

        if (getBaseMapper().selectOne(
                new QueryWrapper<AppApi>().eq("path", params.getPath())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口路径");
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<AppApi>().eq("alias", params.getAlias())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口别名");
        }

        AppApi appApi = getMapperFacade().map(params, AppApi.class);
        save(appApi);
        return "添加API接口信息成功";
    }

    @Override
    public String delete(Long fid) {
        AppApi appApi = getBaseMapper().selectById(fid);
        if (appApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        getBaseMapper().deleteById(fid);
        return "删除接口【" + appApi.getPath() + "】成功";
    }

    @Override
    public String updateApi(UpdateApiParams params) {
        BeanValidator.verifyDto(params);
        if (params.getDescription() != null) {
            params.setDescription(params.getDescription().trim());
        }
        if (params.getAlias() != null) {
            params.setAlias(params.getAlias().trim());
        }

        AppApi appApi = getBaseMapper().selectById(params.getFid());
        if (appApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        if (!appApi.getPath().equals(params.getPath())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<AppApi>().eq("path", params.getPath())) != null) {
                throw new ApiResponseException(ErrorU.CODE_9.C, "接口路径");
            }
        }
        if (!appApi.getAlias().equals(params.getAlias())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<AppApi>().eq("alias", params.getAlias())) != null) {
                throw new ApiResponseException(ErrorU.CODE_9.C, "接口别名");
            }
        }
        appApi = getMapperFacade().map(params, AppApi.class);
        updateById(appApi);
        return "更新接口【" + appApi.getPath() + "】成功";
    }

    @Override
    public AppApiDto getApi(Long fid) {
        AppApi appApi = getBaseMapper().selectById(fid);
        if (appApi == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "接口");
        }
        return getBaseDto(appApi);
    }

    @Override
    public List<AppApiDto> getAllApi() {
        List<AppApiDto> appApiDtoList = getMapperFacade().mapAsList(list(), AppApiDto.class);
        return appApiDtoList;
    }

    @Override
    public List<AppApiDto> getApiListByKeyword(String keyword) {
        QueryWrapper<AppApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("path", "%" + keyword + "%")
                    .or().like("description", "%" + keyword + "%");
        List<AppApi> appApiList = getBaseMapper().selectList(queryWrapper);
        if (appApiList == null) {
            throw new ApiResponseException(ErrorU.CODE_8.C, "接口");
        }
        return getDtoList(appApiList);
    }


}
