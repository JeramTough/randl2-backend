package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;
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
public class RandlModuleServiceImpl extends BaseDtoServiceImpl<RandlModuleMapper, RandlModule, RandlModuleDto>
        implements RandlModuleService {

    private final RandlModuleMapper randlModuleMapper;

    @Autowired
    public RandlModuleServiceImpl(WebApplicationContext wc,
                                  RandlModuleMapper randlModuleMapper) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
    }

    @Override
    protected RandlModuleDto toDto(RandlModule randlModule) {
        return toDto1(randlModule, RandlModuleDto.class);
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
        List<RandlModule> randlModuleList;
        if (UserHolder.isSuperAdmin()) {
            randlModuleList = randlModuleMapper.selectList(null);
        }
        else {
            SystemUser systemUser = UserHolder.getSystemUser();
            //进到这里，默认已经是管理员身份了
            randlModuleList = randlModuleMapper.selectListByRoleId(systemUser.getRandRole().getFid());
        }

        //先将所有的节点实例化出来
        Map<Long, TreeNode> menuIdKeyTreeNodeMap = new HashMap<>(15);
        for (RandlModule randlModule : randlModuleList) {
            TreeNode treeNode = new DefaultTreeNode(randlModule);
            treeNode.setOrder(randlModule.getOrder());
            treeNode.setValue(randlModule);
            menuIdKeyTreeNodeMap.put(randlModule.getFid(), treeNode);
        }

        //组装成根节点
        TreeNode rootTreeNode = new DefaultTreeNode();
        for (RandlModule randlModule : randlModuleList) {
            //如果是第一层的
            if (randlModule.getLevel() == 1) {
                rootTreeNode.addSub(menuIdKeyTreeNodeMap.get(randlModule.getFid()));
            }
            else {
                Long parentMenuId = randlModule.getParentId();
                TreeNode treeNode = menuIdKeyTreeNodeMap.get(randlModule.getFid());
                TreeNode parentTreeNode = menuIdKeyTreeNodeMap.get(parentMenuId);
                parentTreeNode.addSub(treeNode);
            }
        }
        return rootTreeNode;
    }
}
