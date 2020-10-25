package com.jeramtough.randl2.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.service.MyBaseService;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;

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
            throw new ApiResponseException(ErrorU.CODE_10.C, "主键fid");
        }

        T entity = (T) getMapperFacade().map(params, entityFromDb.getClass());

        int resultCount = getBaseMapper().updateById(entity);
        if (resultCount == 0) {
            throw new ApiResponseException(ErrorS.CODE_4.C, "[" + PRIMARY_KEY_NAME + "=" + fid);
        }
        return "更新成功";
    }

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
