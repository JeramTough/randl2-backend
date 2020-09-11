package com.jeramtough.randl2.service.impl;

import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.model.dto.SystemMenuDto;
import com.jeramtough.randl2.model.entity.SystemMenu;
import com.jeramtough.randl2.dao.mapper.SystemMenuMapper;
import com.jeramtough.randl2.service.BaseService;
import com.jeramtough.randl2.service.SystemMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu, SystemMenuDto>
        implements SystemMenuService {

    public SystemMenuServiceImpl(WebApplicationContext wc,
                                 MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    protected SystemMenuDto toDto(SystemMenu systemMenu) {
        return toDto1(systemMenu,SystemMenuDto.class);
    }

    @Override
    public List<SystemMenu> getCurrentAdminUserSystemMenus() {
        SystemUser systemUser=UserHolder.getSystemUser();

        return null;
    }

    @Override
    public List<SystemMenu> getCurrentAdminUserSystemMenuDtos() {
        return null;
    }
}
