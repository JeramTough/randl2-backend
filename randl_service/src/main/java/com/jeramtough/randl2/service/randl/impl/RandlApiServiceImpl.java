package com.jeramtough.randl2.service.randl.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiinfo.bean.ApiInfo;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.mapper.RandlApiMapper;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.entity.RandlApi;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.api.AddApiParams;
import com.jeramtough.randl2.common.model.params.api.ConditionApiParams;
import com.jeramtough.randl2.common.model.params.api.UpdateApiParams;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.randl.RandlApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RandlApiServiceImpl
        extends MyBaseServiceImpl<RandlApiMapper, RandlApi, RandlApiDto>
        implements RandlApiService,
        WithLogger {

    private final RandlAppMapper randlAppMapper;


    @Autowired
    public RandlApiServiceImpl(WebApplicationContext wc,
                               RandlAppMapper randlAppMapper) {
        super(wc);
        this.randlAppMapper = randlAppMapper;
    }


    @Override
    public String registerRandlApi(List<ApiInfo> apiInfoList, Long appId) {

        List<RandlApi> randlApiList =
                apiInfoList
                        .parallelStream()
                        .map(apiInfo -> {
                            RandlApi randlApi = new RandlApi();
                            randlApi.setAlias(apiInfo.getMethodName());
                            randlApi.setGroupName(apiInfo.getGroupName());
                            randlApi.setPath(apiInfo.getPath());
                            randlApi.setDescription(apiInfo.getDescription());
                            randlApi.setAppId(appId);

                            return randlApi;
                        }).toList();
        Map<String, ApiInfo> pathKeyApiInfoMap = apiInfoList
                .parallelStream()
                .collect(Collectors.toMap(ApiInfo::getPath, apiInfo -> apiInfo));

        //查出这个app里所有接口信息
        Map<String, RandlApiDto> pathKeyRandlApiMap = this
                .getPathKeyRandlApiMapByAppId(appId);

        //把系统里没有的api信息，筛出来删掉
        List<Long> deletedApiIdList =
                pathKeyRandlApiMap
                        .values()
                        .parallelStream()
                        .filter(randlApiDto -> !pathKeyApiInfoMap.containsKey(
                                randlApiDto.getPath()))
                        .map(RandlApiDto::getFid)
                        .toList();
        if (!deletedApiIdList.isEmpty()) {
            this.removeByIds(deletedApiIdList);
        }

        //筛出数据库没有的，进行新增
        List<RandlApi> addRandApiList =
                randlApiList
                        .parallelStream()
                        .filter(randlApi -> !pathKeyRandlApiMap.containsKey(
                                randlApi.getPath()))
                        .peek(randlApi -> randlApi.setCreateTime(new Date()))
                        .toList();
        if (!addRandApiList.isEmpty()) {
            this.saveBatch(addRandApiList);
        }

        //筛出有更改的，进行更新
        List<RandlApi> updateRandApiList =
                randlApiList
                        .parallelStream()
                        .filter(randlApi -> {
                            boolean isContains = pathKeyRandlApiMap.containsKey(
                                    randlApi.getPath());
                            if (isContains) {
                                RandlApiDto entity = pathKeyRandlApiMap.get(
                                        randlApi.getPath());
                                if (randlApi.getAlias().equals(entity.getAlias()) &&
                                        randlApi.getGroupName().equals(
                                                entity.getGroupName()) &&
                                        randlApi.getPath().equals(entity.getPath()) &&
                                        randlApi.getDescription().equals(
                                                entity.getDescription())) {
                                    return false;
                                }
                                else {
                                    randlApi.setFid(entity.getFid());
                                    randlApi.setUpdateTime(new Date());
                                    return true;
                                }
                            }
                            else {
                                return false;
                            }
                        })
                        .toList();
        if (!updateRandApiList.isEmpty()) {
            this.updateBatchById(updateRandApiList);
        }

        return "登记完成";
    }

    @Override
    public String addApi(AddApiParams params) {
        BeanValidator.verifyParams(params);
        params.setDescription(params.getDescription().trim());
        params.setAlias(params.getAlias().trim());

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlApi>()
                        .eq("app_id", params.getAppId())
                        .eq("path", params.getPath())) != null) {
            throw new ApiResponseException(ErrorU.CODE_11.C, "该应用的-接口路径");
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlApi>()
                        .eq("app_id", params.getAppId())
                        .eq("alias", params.getAlias())) != null) {
            throw new ApiResponseException(ErrorU.CODE_11.C, "该应用的-接口别名");
        }

        RandlApi randlApi = BeanUtil.copyProperties(params, RandlApi.class);
        randlApi.setCreateTime(new Date());
        getBaseMapper().insert(randlApi);
        return "添加API接口信息成功";
    }

    @Override
    public String delete(Long fid) {
        RandlApi randlApi = getBaseMapper().selectById(fid);
        if (randlApi == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口");
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
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口");
        }
        if (!randlApi.getPath().equals(params.getPath())) {

            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlApi>()
                            .eq("app_id", params.getAppId())
                            .eq("path", params.getPath())) != null) {
                throw new ApiResponseException(ErrorU.CODE_11.C, "该应用的-接口路径");
            }

        }
        if (!randlApi.getAlias().equals(params.getAlias())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlApi>()
                            .eq("app_id", params.getAppId())
                            .eq("alias", params.getAlias())) != null) {
                throw new ApiResponseException(ErrorU.CODE_11.C, "该应用的-接口别名");
            }
        }
        randlApi = BeanUtil.copyProperties(params, RandlApi.class);
        updateById(randlApi);
        return "更新接口【" + randlApi.getPath() + "】成功";
    }

    @Override
    public RandlApiDto getApi(Long fid) {
        RandlApi randlApi = getBaseMapper().selectById(fid);
        if (randlApi == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口");
        }
        return getBaseDto(randlApi);
    }

    @Override
    public List<RandlApiDto> getAllApi() {
        return getDtoList(list());
    }

    @Override
    public List<RandlApiDto> getApiListByKeyword(String keyword) {
        QueryWrapper<RandlApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("path", keyword)
                    .or().like("description", keyword);
        List<RandlApi> randlApiList = getBaseMapper().selectList(queryWrapper);
        if (randlApiList == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "接口");
        }
        return getDtoList(randlApiList);
    }

    @Override
    public List<RandlApiDto> getListByAppId(Long appId) {
        if (randlAppMapper.selectById(appId) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
        }
        QueryWrapper<RandlApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", appId);

        List<RandlApi> randlApiList = getBaseMapper().selectList(queryWrapper);
        if (randlApiList == null) {
            randlApiList = new ArrayList<>();
        }
        return getDtoList(randlApiList);
    }

    @Override
    public Map<String, RandlApiDto> getPathKeyRandlApiMapByAppId(Long appId) {
        Map<String, RandlApiDto> pathKeyRandlApiMap =
                this
                        .getListByAppId(appId)
                        .parallelStream()
                        .collect(Collectors.toMap(RandlApiDto::getPath, randlApi -> randlApi));
        return pathKeyRandlApiMap;
    }

    @Override
    public void setCondition(BaseConditionParams params, QueryWrapper<RandlApi> queryWrapper) {
        super.setCondition(params, queryWrapper);
        ConditionApiParams paramsForApi = (ConditionApiParams) params;

        queryWrapper.nested(warpper -> warpper.eq("app_id", paramsForApi.getAppId()));

        if (params.getKeyword() != null) {
            queryWrapper.and(wrapper ->
                    wrapper.like("alias", params.getKeyword())
                           .or()
                           .eq("fid", params.getKeyword())
                           .or()
                           .like("path", params.getKeyword())
                           .or()
                           .like("description", params.getKeyword()));
        }

    }

  /*  @Override
    public PageDto<RandlApiDto> pageByConditionTwo(QueryByPageParams queryByPageParams, BaseConditionParams params,
                                                   QueryWrapper<RandlApi> queryWrapper) {
        ConditionApiParams paramsForApi = (ConditionApiParams) params;

        queryWrapper.nested(warpper -> warpper.eq("app_id", paramsForApi.getAppId()));

        if (params.getKeyword() != null) {
            queryWrapper.and(wrapper ->
                    wrapper.like("alias", params.getKeyword())
                           .or()
                           .eq("fid", params.getKeyword())
                           .or()
                           .like("path", params.getKeyword())
                           .or()
                           .like("description", params.getKeyword()));
        }

        return super.pageByConditionThree(queryByPageParams, paramsForApi, queryWrapper);
    }*/


}
