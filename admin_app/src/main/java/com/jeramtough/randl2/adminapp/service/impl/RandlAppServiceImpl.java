package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.service.RandlAppService;
import com.jeramtough.randl2.common.component.appdetail.RandlAdminApp;
import com.jeramtough.randl2.common.component.appdetail.RandlUserApp;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Service
public class RandlAppServiceImpl extends MyBaseServiceImpl<RandlAppMapper, RandlApp, RandlAppDto>
        implements RandlAppService {

    private final RandlModuleMapper randlModuleMapper;
    private final RandlRoleMapper randlRoleMapper;

    @Autowired
    public RandlAppServiceImpl(WebApplicationContext wc,
                               RandlModuleMapper randlModuleMapper,
                               RandlRoleMapper randlRoleMapper) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
        this.randlRoleMapper = randlRoleMapper;
    }

    @Override
    protected RandlAppDto toDto(RandlApp randlApp) {
        return toDto1(randlApp, RandlAppDto.class);
    }

    @Override
    public String add(AddAppParams params) {
        BeanValidator.verifyParams(params);

        RandlApp randlApp = getMapperFacade().map(params, RandlApp.class);
        randlApp.setCreateTime(LocalDateTime.now());
        randlApp.setIsAble(1);

        String appCode = IdUtil.getUUID();
        randlApp.setAppCode(appCode);
        return "添加应用[" + appCode + "]成功！";
    }

    @Override
    public String removeOneById(Serializable id) {

        checkIsBossRandlApp(Long.parseLong(id.toString()));

        randlModuleMapper.delete(new QueryWrapper<RandlModule>().eq("app_id", id));
        randlRoleMapper.delete(new QueryWrapper<RandlRole>().eq("app_id", id));
        getBaseMapper().deleteById(id);
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
    public PageDto<RandlAppDto> pageByCondition(QueryByPageParams queryByPageParams, ConditionAppParams params) {
        BeanValidator.verifyParams(params);

        QueryPage<RandlApp> randlUserQueryPage = getBaseMapper().selectByCondition(
                new QueryPage<>(queryByPageParams), params);

        return toPageDto(randlUserQueryPage);
    }

    //*************

    private void checkIsBossRandlApp(Long fid) {
        if (fid.equals(RandlAdminApp.FID) || fid.equals(RandlUserApp.FID)) {
            throw new ApiResponseException(ErrorU.CODE_701.C);
        }
    }

}
