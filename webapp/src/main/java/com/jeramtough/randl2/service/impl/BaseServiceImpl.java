package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.component.db.QueryPage;
import com.jeramtough.randl2.dao.entity.AdminUser;
import com.jeramtough.randl2.dto.AdminUserDto;
import com.jeramtough.randl2.dto.PageDto;
import com.jeramtough.randl2.service.BaseService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2020/1/30 14:41
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T, D>
        extends ServiceImpl<M, T> implements BaseService<T, D> {

    private WebApplicationContext wc;
    private MapperFacade mapperFacade;

    public BaseServiceImpl(WebApplicationContext wc,
                           MapperFacade mapperFacade) {
        this.wc = wc;
        this.mapperFacade = mapperFacade;
    }

    public WebApplicationContext getWC() {
        return wc;
    }

    public MapperFacade getMapperFacade() {
        return mapperFacade;
    }

    public D getBaseDto(T t) {
        return toDto(t);
    }

    public List<D> getDtoList(List<T> tList) {
        List<D> dList = new ArrayList<>(tList.size());
        for (T t : tList) {
            dList.add(toDto(t));
        }
        return dList;
    }

    @Override
    public PageDto<D> getBaseDtoListByPage(QueryByPageParams queryByPageParams) {
        BeanValidator.verifyDto(queryByPageParams);
        QueryPage<T> queryPage = getBaseMapper().selectPage(
                new QueryPage<>(queryByPageParams), null);
        return getPageDto(queryPage);
    }

    @Override
    public List<D> getAllBaseDto() {
        List<T> list = getBaseMapper().selectList(null);
        return getDtoList(list);
    }

    public PageDto<D> getPageDto(QueryPage<T> queryPage) {
        PageDto<D> pageDto = new PageDto<>();
        pageDto.setIndex(queryPage.getCurrent());
        pageDto.setSize(queryPage.getSize());
        pageDto.setTotal(queryPage.getTotal());
        pageDto.setList(getDtoList(queryPage.getRecords()));
        return pageDto;
    }

    protected abstract D toDto(T t);
}
