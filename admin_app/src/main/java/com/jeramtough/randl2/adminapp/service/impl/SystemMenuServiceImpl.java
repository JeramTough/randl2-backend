package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.randl2.common.model.dto.SystemMenuDto;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
import com.jeramtough.randl2.common.mapper.SystemMenuMapper;
import com.jeramtough.randl2.adminapp.service.SystemMenuService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

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
    public TreeNode getCurrentAdminUserSystemMenuTree() {
        return null;
    }
}
