package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.model.params.permission.AddApiParams;
import com.jeramtough.randl2.model.params.permission.UpdateApiParams;
import com.jeramtough.randl2.model.entity.Api;
import com.jeramtough.randl2.dao.mapper.ApiMapper;
import com.jeramtough.randl2.model.dto.ApiDto;
import com.jeramtough.randl2.service.ApiService;
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
public class ApiServiceImpl extends BaseServiceImpl<ApiMapper, Api, ApiDto>
        implements ApiService,
        WithLogger {


    public ApiServiceImpl(WebApplicationContext wc,
                          MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected ApiDto toDto(Api api) {
        return getMapperFacade().map(api, ApiDto.class);
    }

    @Override
    public String addApi(AddApiParams params) {
        BeanValidator.verifyDto(params);
        params.setDescription(params.getDescription().trim());
        params.setAlias(params.getAlias().trim());

        if (getBaseMapper().selectOne(
                new QueryWrapper<Api>().eq("path", params.getPath())) != null) {
            throw new ApiResponseException(4001);
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<Api>().eq("alias", params.getAlias())) != null) {
            throw new ApiResponseException(4002);
        }

        Api api = getMapperFacade().map(params, Api.class);
        save(api);
        return "添加API接口信息成功";
    }

    @Override
    public String delete(Long fid) {
        Api api = getBaseMapper().selectById(fid);
        if (api == null) {
            throw new ApiResponseException(4010);
        }
        getBaseMapper().deleteById(fid);
        return "删除接口【" + api.getPath() + "】成功";
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

        Api api = getBaseMapper().selectById(params.getFid());
        if (api == null) {
            throw new ApiResponseException(4021);
        }
        if (!api.getPath().equals(params.getPath())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<Api>().eq("path", params.getPath())) != null) {
                throw new ApiResponseException(4001);
            }
        }
        if (!api.getAlias().equals(params.getAlias())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<Api>().eq("alias", params.getAlias())) != null) {
                throw new ApiResponseException(4002);
            }
        }
        api = getMapperFacade().map(params, Api.class);
        updateById(api);
        return "更新接口【" + api.getPath() + "】成功";
    }

    @Override
    public ApiDto getApi(Long fid) {
        Api api = getBaseMapper().selectById(fid);
        if (api == null) {
            throw new ApiResponseException(4030);
        }
        return getBaseDto(api);
    }

    @Override
    public List<ApiDto> getAllApi() {
        List<ApiDto> apiDtoList = getMapperFacade().mapAsList(list(), ApiDto.class);
        return apiDtoList;
    }

    @Override
    public List<ApiDto> getApiListByKeyword(String keyword) {
        QueryWrapper<Api> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("path", "%" + keyword + "%")
                    .or().like("description", "%" + keyword + "%");
        List<Api> apiList = getBaseMapper().selectList(queryWrapper);
        if (apiList == null) {
            throw new ApiResponseException(4040);
        }
        return getDtoList(apiList);
    }


}
