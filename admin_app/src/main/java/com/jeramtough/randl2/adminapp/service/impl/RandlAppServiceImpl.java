package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.service.RandlAppService;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.entity.RandRole;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
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
public class RandlAppServiceImpl extends BaseDtoServiceImpl<RandlAppMapper, RandlApp, RandlAppDto>
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
        randlModuleMapper.delete(new QueryWrapper<RandlModule>().eq("app_id", id));
        randlRoleMapper.delete(new QueryWrapper<RandRole>().eq("app_id", id));
        getBaseMapper().deleteById(id);
        return "删除App成功！";
    }

    @Override
    public String update(UpdateAppParams params) {
        BeanValidator.verifyParams(params);

        RandlApp randlApp=getMapperFacade().map(params,RandlApp.class);
        getBaseMapper().updateById(randlApp);
        return "更新成功！";
    }


    @Override
    public List<RandlAppDto> getListByKeyword(String keyword) {
        return null;
    }
}
