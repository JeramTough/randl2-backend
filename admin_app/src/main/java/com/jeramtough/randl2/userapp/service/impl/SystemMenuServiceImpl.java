package com.jeramtough.randl2.userapp.service.impl;

import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.dto.SystemMenuDto;
import com.jeramtough.randl2.common.model.entity.SystemMenu;
import com.jeramtough.randl2.common.mapper.SystemMenuMapper;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.userapp.service.SystemMenuService;
import ma.glasnost.orika.MapperFacade;
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
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu, SystemMenuDto>
        implements SystemMenuService {

    private final SystemMenuMapper systemMenuMapper;

    @Autowired
    public SystemMenuServiceImpl(WebApplicationContext wc,
                                 MapperFacade mapperFacade,
                                 SystemMenuMapper systemMenuMapper) {
        super(wc, mapperFacade);
        this.systemMenuMapper = systemMenuMapper;
    }

    @Override
    protected SystemMenuDto toDto(SystemMenu systemMenu) {
        return toDto1(systemMenu, SystemMenuDto.class);
    }


    @Override
    public TreeStructure getCurrentAdminUserSystemMenuTree() {
        TreeNode rootTreeNode = getRootTreeNode();
        TreeStructure treeStructure = TreeNodeUtils.toTreeStructure(rootTreeNode);
        return treeStructure;
    }

    @Override
    public Map<String, Object> getCurrentAdminUserSystemMenuTreeMap() {
        TreeNode rootTreeNode = getRootTreeNode();
        Map<String, Object> treeMap = TreeNodeUtils.toTreeMap(rootTreeNode);
        return treeMap;
    }

    //**********************

    private TreeNode getRootTreeNode() {
        List<SystemMenu> systemMenuList;
        if (UserHolder.isSuperAdmin()) {
            systemMenuList = systemMenuMapper.selectList(null);
        }
        else {
            SystemUser systemUser = UserHolder.getSystemUser();
            //进到这里，默认已经是管理员身份了
            systemMenuList = systemMenuMapper.selectListByRoleId(systemUser.getRole().getFid());
        }

        //先将所有的节点实例化出来
        Map<Long, TreeNode> menuIdKeyTreeNodeMap = new HashMap<>(15);
        for (SystemMenu systemMenu : systemMenuList) {
            TreeNode treeNode = new DefaultTreeNode(systemMenu);
            treeNode.setOrder(systemMenu.getMenuOrder());
            treeNode.setValue(systemMenu);
            menuIdKeyTreeNodeMap.put(systemMenu.getFid(), treeNode);
        }

        //组装成根节点
        TreeNode rootTreeNode = new DefaultTreeNode();
        for (SystemMenu systemMenu : systemMenuList) {
            //如果是第一层的
            if (systemMenu.getLevel() == 1) {
                rootTreeNode.addSub(menuIdKeyTreeNodeMap.get(systemMenu.getFid()));
            }
            else {
                Long parentMenuId = systemMenu.getParentId();
                TreeNode treeNode = menuIdKeyTreeNodeMap.get(systemMenu.getFid());
                TreeNode parentTreeNode = menuIdKeyTreeNodeMap.get(parentMenuId);
                parentTreeNode.addSub(treeNode);
            }
        }
        return rootTreeNode;
    }
}
