package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Service
public class RandlModuleServiceImpl extends BaseDtoServiceImpl<RandlModuleMapper, RandlModule, RandlModuleDto>
        implements RandlModuleService {

    private final RandlModuleMapper randlModuleMapper;
    private final AppSetting appSetting;

    @Autowired
    public RandlModuleServiceImpl(WebApplicationContext wc,
                                  RandlModuleMapper randlModuleMapper,
                                  AppSetting appSetting) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
        this.appSetting = appSetting;
    }

    @Override
    protected RandlModuleDto toDto(RandlModule randlModule) {
        return toDto1(randlModule, RandlModuleDto.class);
    }


    @Override
    public Map<String, Object> getRandlModuleTreeMap(Long appId, Long uid) {
        return null;
    }


    //**********************




}
