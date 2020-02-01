package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.dao.entity.Api;
import com.jeramtough.randl2.dao.mapper.ApiMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.randl2.dto.ApiDto;
import com.jeramtough.randl2.service.ApiService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

    private final MapperFacade mapperFacade;

    @Autowired
    public ApiServiceImpl(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    @Override
    public String addApi(AddApiParams params) {
        BeanValidator.verifyDto(params);
        if (getBaseMapper().selectOne(
                new QueryWrapper<Api>().eq("path", params.getPath())) != null) {
            throw new ApiResponseException(4001);
        }

        Api api = mapperFacade.map(params, Api.class);
        save(api);
        return "添加API接口信息成功";
    }

    @Override
    public String delete(Long apiId) {
        Api api = getBaseMapper().selectById(apiId);
        if (api == null) {
            throw new ApiResponseException(4010);
        }
        return "删除接口【" + api.getPath() + "】成功";
    }

    @Override
    public String updateApi(UpdateApiParams params) {
        Api api = getBaseMapper().selectById(params.getFid());
        if (api == null) {
            throw new ApiResponseException(4021);
        }
        api = mapperFacade.map(params, Api.class);
        updateById(api);
        return "更新接口【" + api.getPath() + "】成功";
    }

    @Override
    public ApiDto getApi(Long apiId) {
        Api api = getBaseMapper().selectById(apiId);
        if (api == null) {
            throw new ApiResponseException(4030);
        }
        ApiDto apiDto = mapperFacade.map(api, ApiDto.class);
        return apiDto;
    }

    @Override
    public List<ApiDto> getAllApi() {
        List<ApiDto> apiDtoList = mapperFacade.mapAsList(list(), ApiDto.class);
        return apiDtoList;
    }
}
