package com.jeramtough.randl2.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import com.jeramtough.randl2.service.base.MyBaseService;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * Created on 2020/10/25 16:43
 * by @author WeiBoWen
 * </pre>
 */
public abstract class MyBaseServiceImpl<M extends BaseMapper<T>, T, D>
        extends BaseDtoServiceImpl<M, T, D> implements MyBaseService<T, D> {

    /**
     * 数据库主键名
     */
    private static final String PRIMARY_KEY_NAME = "fid";

    public MyBaseServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    public String updateByParams(Object params) {
        BeanValidator.verifyParams(params);

        Long fid = getPrimaryKeyValue(params);
        T entityFromDb = getBaseMapper().selectById(fid);
        if (entityFromDb == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "主键" + PRIMARY_KEY_NAME);
        }

        T entity = (T) getMapperFacade().map(params, entityFromDb.getClass());

        int resultCount = getBaseMapper().updateById(entity);
        if (resultCount == 0) {
            throw new ApiResponseException(ErrorS.CODE_5.C, "[" + PRIMARY_KEY_NAME + "=" + fid);
        }
        return "更新成功";
    }

    @Override
    public String updateByParamsList(List<?> params) {
        params.parallelStream()
              .forEach(this::updateByParams);
        return "批量更新成功";
    }

    @Override
    public String removeOneById(Long fid) {
        if (getBaseMapper().selectById(fid) == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "fid对应");
        }

        if (getBaseMapper().deleteById(fid) == 0) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "删除资源");
        }
        return "删除成功！";
    }

    @Override
    public PageDto<D> pageByCondition(QueryByPageParams queryByPageParams, BaseConditionParams params) {
        BeanValidator.verifyParams(params);

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        return pageByConditionTwo(queryByPageParams, params, queryWrapper);
    }

    public PageDto<D> pageByConditionTwo(QueryByPageParams queryByPageParams, BaseConditionParams params,
                                         QueryWrapper<T> queryWrapper) {

        return pageByConditionThree(queryByPageParams, params, queryWrapper);
    }

    public PageDto<D> pageByConditionThree(QueryByPageParams queryByPageParams, BaseConditionParams params,
                                           QueryWrapper<T> queryWrapper) {


        if (params.getStartDate() != null && params.getEndDate() != null) {
            queryWrapper.and(wrapper ->
                    wrapper.between("create_time", params.getStartDate(), params.getEndDate()));
        }

        QueryPage<T> queryPage =
                getBaseMapper().selectPage(new QueryPage<>(queryByPageParams),
                        queryWrapper);

        return toPageDto(queryPage);
    }

    @Override
    public boolean save(T entity) {
        boolean isSuccessful = super.save(entity);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "保存");
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        boolean isSuccessful = super.saveOrUpdate(entity);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "保存或者更新");
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
        boolean isSuccessful = super.saveOrUpdate(entity, updateWrapper);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "保存或者更新");
        }
        return true;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        boolean isSuccessful = super.saveBatch(entityList);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "批量保存");
        }
        return true;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        boolean isSuccessful = super.saveBatch(entityList, batchSize);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "批量保存");
        }
        return true;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        boolean isSuccessful = super.saveOrUpdateBatch(entityList);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "批量保存或者更新");
        }
        return true;
    }


    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        boolean isSuccessful = super.saveOrUpdateBatch(entityList, batchSize);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "批量保存或者更新");
        }
        return true;
    }

    @Override
    public boolean remove(Wrapper<T> queryWrapper) {
        boolean isSuccessful = super.remove(queryWrapper);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "删除");
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean isSuccessful = super.removeById(id);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "根据Id删除");
        }
        return true;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean isSuccessful = super.removeByIds(idList);
        if (!isSuccessful) {
            throw new ApiResponseException(ErrorS.CODE_2.C, "根据Id批量删除");
        }
        return true;
    }

    @Override
    public T getById(Serializable id) {
        T t = super.getById(id);
        if (t == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "fid对应");
        }
        return t;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        T t = super.getOne(queryWrapper);
        if (t == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "fid对应");
        }
        return t;
    }

    //***********************************

    private Long getPrimaryKeyValue(Object params) {
        try {
            Field field = params.getClass().getDeclaredField(PRIMARY_KEY_NAME);
            field.setAccessible(true);
            Long fid = (Long) field.get(params);
            return fid;
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ApiResponseBeanException(ErrorU.CODE_1.C, PRIMARY_KEY_NAME);
        }
    }
}
