package com.jeramtough.randl2.service.randl.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.mapper.RandlApiMapper;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.detail.appdetail.RandlAdminApp;
import com.jeramtough.randl2.common.model.detail.appdetail.RandlUserApp;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandlApi;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.randl.RandlAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

//import com.jeramtough.randl2.common.model.detail.appdetail.RandlUserApp;
//import com.jeramtough.randl2.common.model.detail.appdetail.RandlAdminApp;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Service
public class RandlAppServiceImpl
        extends MyBaseServiceImpl<RandlAppMapper, RandlApp, RandlAppDto>
        implements RandlAppService {

    private final RandlModuleMapper randlModuleMapper;
    private final RandlRoleMapper randlRoleMapper;
    private final RandlApiMapper randlApiMapper;

    @Autowired
    public RandlAppServiceImpl(WebApplicationContext wc,
                               RandlModuleMapper randlModuleMapper,
                               RandlRoleMapper randlRoleMapper,
                               RandlApiMapper randlApiMapper) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
        this.randlRoleMapper = randlRoleMapper;
        this.randlApiMapper = randlApiMapper;
    }


    @Override
    public String add(AddAppParams params) {
        BeanValidator.verifyParams(params);

        RandlApp randlApp = BeanUtil.copyProperties(params, RandlApp.class);
        randlApp.setCreateTime(new Date());
        randlApp.setIsAble(1);


        getBaseMapper().insert(randlApp);

        return "添加应用[" + randlApp.getAppName() + "]成功！";
    }


    @Override
    public String removeOneById(Long fid) {
        checkIsBossRandlApp(fid);

        //后面在数据库表添加了外键，其实这些不需要了的
        randlModuleMapper.delete(new QueryWrapper<RandlModule>().eq("app_id", fid));
        randlApiMapper.delete(new QueryWrapper<RandlApi>().eq("app_id", fid));
        randlRoleMapper.delete(new QueryWrapper<RandlRole>().eq("app_id", fid));

        getBaseMapper().deleteById(fid);
        return "删除App成功！";
    }


    @Override
    public String update(UpdateAppParams params) {
        BeanValidator.verifyParams(params);

        checkIsBossRandlApp(params.getFid());

        return updateByParams(params);
    }


    @Override
    public List<RandlAppDto> getListByKeyword(String keyword) {
        return null;
    }

    @Override
    public PageDto<RandlAppDto> pageByCondition(QueryByPageParams queryByPageParams,
                                                ConditionAppParams params) {
        BeanValidator.verifyParams(params);

        QueryPage<RandlApp> randlUserQueryPage = getBaseMapper().selectByCondition(
                new QueryPage<>(queryByPageParams), params);

        return toPageDto(randlUserQueryPage);
    }

    @Override
    public List<RandlAppDto> getAllOnlyName() {
        List<RandlAppDto> dtoList = getBaseMapper().selectAllOnlyName();
        return dtoList;
    }

    //*************

    private void checkIsBossRandlApp(Long fid) {
        if (fid.equals(RandlAdminApp.FID) || fid.equals(RandlUserApp.FID)) {
            throw new ApiResponseException(ErrorU.CODE_701.C);
        }
    }

}
