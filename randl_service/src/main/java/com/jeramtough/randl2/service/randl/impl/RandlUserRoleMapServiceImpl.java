package com.jeramtough.randl2.service.randl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.service.randl.RandlUserRoleMapService;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlUserRoleMapDto;
import com.jeramtough.randl2.common.model.entity.RandlUserRoleMap;
import com.jeramtough.randl2.common.model.params.user.SetUserRoleMapParams;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2020/11/7 2:07
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class RandlUserRoleMapServiceImpl extends MyBaseServiceImpl<RandlUserRoleMapMapper, RandlUserRoleMap,
        RandlUserRoleMapDto> implements RandlUserRoleMapService {

    private final AppSetting appSetting;

    public RandlUserRoleMapServiceImpl(WebApplicationContext wc,
                                       AppSetting appSetting) {
        super(wc);
        this.appSetting = appSetting;
    }

    @Override
    protected RandlUserRoleMapDto toDto(RandlUserRoleMap randlUserRoleMap) {
        return toDto1(randlUserRoleMap, RandlUserRoleMapDto.class);
    }

    @Override
    public String setUserRoleMap(SetUserRoleMapParams params) {

        //过滤掉默认的RandlUserId
        List<Long> roleIds = params.getRoleIds()
                                   .parallelStream()
                                   .filter(roleId -> !appSetting.getDefaultUserRoleId().equals(roleId))
                                   .collect(Collectors.toList());
        getBaseMapper().delete(new QueryWrapper<RandlUserRoleMap>().eq("uid", params.getUid()));

        roleIds.parallelStream().forEach(roleId -> {
            RandlUserRoleMap randlUserRoleMap = new RandlUserRoleMap();
            randlUserRoleMap.setRoleId(roleId);
            randlUserRoleMap.setUid(params.getUid());
            getBaseMapper().insert(randlUserRoleMap);
        });
        return "设置用户角色关系成功！";
    }
}
