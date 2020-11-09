package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.randl2.adminapp.service.RandlModuleApiMapService;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.mapper.RandlModuleApiMapMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleApiMapDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModuleApiMap;
import com.jeramtough.randl2.common.model.params.module.SetModuleApiMapParams;
import com.jeramtough.randl2.common.model.params.module.TreeModuleParams;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-26
 */
@Service
public class RandlModuleApiMapServiceImpl extends MyBaseServiceImpl<RandlModuleApiMapMapper, RandlModuleApiMap,
        RandlModuleApiMapDto> implements RandlModuleApiMapService {

    private final RandlModuleMapper randlModuleMapper;
    private final RandlModuleService randlModuleService;

    public RandlModuleApiMapServiceImpl(WebApplicationContext wc,
                                        RandlModuleMapper randlModuleMapper,
                                        RandlModuleService randlModuleService) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
        this.randlModuleService = randlModuleService;
    }

    @Override
    protected RandlModuleApiMapDto toDto(RandlModuleApiMap randlModuleApiMap) {
        return toDto1(randlModuleApiMap, RandlModuleApiMapDto.class);
    }

    @Override
    public List<Long> getApiListByAppIdAndModuleId(Long appId, Long moduleId) {
        QueryWrapper<RandlModuleApiMap> queryWrapper = new QueryWrapper();
        queryWrapper.eq("app_id", appId);
        queryWrapper.eq("module_id", moduleId);
        List<RandlModuleApiMap> list = getBaseMapper().selectList(queryWrapper);
        List<Long> apiIds = list.parallelStream()
                                .map(RandlModuleApiMap::getApiId)
                                .collect(Collectors.toList());
        return apiIds;
    }

    @Override
    public String setModuleApiMap(SetModuleApiMapParams params) {
        BeanValidator.verifyParams(params);

        //得到这个moduleId的节点
        TreeModuleParams treeModuleParams = new TreeModuleParams();
        treeModuleParams.setAppId(params.getAppId());
        treeModuleParams.setModuleId(params.getModuleId());
        TreeNode treeNodeForModule = randlModuleService.getTreeModule(treeModuleParams);

        //得到当前模块的所有父模块
        List<TreeNode> parentTreeNodeList = new ArrayList<>();
        TreeNode tempTreeNode=treeNodeForModule;
        while (tempTreeNode.getParent() != null && tempTreeNode.getParent().getValue() != null) {
            parentTreeNodeList.add(tempTreeNode.getParent());
            tempTreeNode=treeNodeForModule.getParent();
        }

        //算出当前模块要删除的api
        QueryWrapper<RandlModuleApiMap> queryWrapper = new QueryWrapper();
        queryWrapper.eq("app_id", params.getAppId());
        queryWrapper.eq("module_id", params.getModuleId());
        List<Long> removeApiIdList = getBaseMapper()
                .selectList(queryWrapper)
                .parallelStream()
                .map(RandlModuleApiMap::getApiId)
                .filter(apiId -> !params.getApiIds().contains(apiId))
                .collect(Collectors.toList());


        //当前模块增加的api，子模块也都增加，当前模块删除的api，子模块也删
        treeNodeForModule.foreach(treeNode -> {
            RandlModuleDto randlModuleDto = (RandlModuleDto) treeNode.getValue();
            addModuleApiMap(params.getAppId(), randlModuleDto.getFid(), params.getApiIds());
            removeModuleApiMap(params.getAppId(), randlModuleDto.getFid(), removeApiIdList);
            return true;
        });

        //当前模块增加的api，其父模块也要增加
        parentTreeNodeList.parallelStream().forEach(treeNode -> {
            RandlModuleDto randlModuleDto = (RandlModuleDto) treeNode.getValue();
            addModuleApiMap(params.getAppId(), randlModuleDto.getFid(), params.getApiIds());
        });


        return "执行成功!";
    }

    //*************

    private void addModuleApiMap(long appId, long moduleId, List<Long> apiIds) {

        if (apiIds.size() > 0) {
            //需要增加的模块和接口映射关系实体对象，如果本身已经有了，就干掉这些
            QueryWrapper<RandlModuleApiMap> queryWrapper = new QueryWrapper();
            queryWrapper.eq("app_id", appId);
            queryWrapper.eq("module_id", moduleId);
            queryWrapper.in("api_id", apiIds);

            List<Long> addApiIdList = new ArrayList<>(apiIds);

            getBaseMapper()
                    .selectList(queryWrapper)
                    .parallelStream()
                    .map(RandlModuleApiMap::getApiId)
                    .forEach(addApiIdList::remove);

            addApiIdList.parallelStream().forEach(apiId -> {
                RandlModuleApiMap moduleApiMap = new RandlModuleApiMap();
                moduleApiMap.setAppId(appId);
                moduleApiMap.setModuleId(moduleId);
                moduleApiMap.setApiId(apiId);
                getBaseMapper().insert(moduleApiMap);
            });

        }
    }

    private void removeModuleApiMap(long appId, long moduleId, List<Long> apiIds) {
        //如果要被移除的apiId数量大于0
        if (apiIds.size() > 0) {
            //需要增加的模块和接口映射关系实体对象，如果本身已经有了，就干掉这些
            QueryWrapper<RandlModuleApiMap> queryWrapper = new QueryWrapper();
            queryWrapper.eq("app_id", appId);
            queryWrapper.eq("module_id", moduleId);
            queryWrapper.in("api_id", apiIds);

            List<Long> removeApiIdList = getBaseMapper()
                    .selectList(queryWrapper)
                    .parallelStream()
                    .map(RandlModuleApiMap::getApiId)
                    .filter(apiId -> apiIds.contains(apiId))
                    .collect(Collectors.toList());


            QueryWrapper<RandlModuleApiMap> removeQueryWrapper = new QueryWrapper();
            removeQueryWrapper.eq("app_id", appId);
            removeQueryWrapper.eq("module_id", moduleId);
            removeQueryWrapper.in("api_id", removeApiIdList);
            getBaseMapper().delete(removeQueryWrapper);


        }
    }

}
