package com.jeramtough.randl2.adminapp.component.userdetail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleRoleMapMapper;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/10/3 23:29
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class ModuleHolder {

    private final RandlModuleMapper randlModuleMapper;
    private final RandlModuleRoleMapMapper randlModuleRoleMapMapper;
    private final AppSetting appSetting;

    @Autowired
    public ModuleHolder(RandlModuleMapper randlModuleMapper,
                        RandlModuleRoleMapMapper randlModuleRoleMapMapper,
                        AppSetting appSetting) {
        this.randlModuleMapper = randlModuleMapper;
        this.randlModuleRoleMapMapper = randlModuleRoleMapMapper;
        this.appSetting = appSetting;
    }

    private TreeNode getRootTreeNode(Long appId, Long uid) {
        /*List<RandlModule> randlModuleList;
        if (UserHolder.isSuperAdmin(uid)) {
            randlModuleList = randlModuleMapper.selectList(new QueryWrapper<RandlModule>().eq("app_id", appId));
        }
        else {

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
        return rootTreeNode;*/
        return null;
    }

}
