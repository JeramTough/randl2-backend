package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.processor.TreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.component.moduletree.RandlModuleDtoOneTreeNodeAdapter;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.model.params.module.AddRandlModuleParams;
import com.jeramtough.randl2.common.model.params.module.ConditionModuleParams;
import com.jeramtough.randl2.common.model.params.module.TreeModuleParams;
import com.jeramtough.randl2.common.model.params.module.UpdateModuleParams;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Service
public class RandlModuleServiceImpl extends MyBaseServiceImpl<RandlModuleMapper, RandlModule, RandlModuleDto>
        implements RandlModuleService {

    private final RandlModuleMapper randlModuleMapper;
    private final AppSetting appSetting;

    private final RandlRoleMapper randlRoleMapper;
    private final RandlAppMapper randlAppMapper;

    @Autowired
    public RandlModuleServiceImpl(WebApplicationContext wc,
                                  RandlModuleMapper randlModuleMapper,
                                  AppSetting appSetting,
                                  RandlRoleMapper randlRoleMapper,
                                  RandlAppMapper randlAppMapper) {
        super(wc);
        this.randlModuleMapper = randlModuleMapper;
        this.appSetting = appSetting;
        this.randlRoleMapper = randlRoleMapper;
        this.randlAppMapper = randlAppMapper;
    }

    @Override
    protected RandlModuleDto toDto(RandlModule randlModule) {
        RandlModuleDto randlModuleDto = getMapperFacade().map(randlModule, RandlModuleDto.class);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        randlModuleDto.setMyCreateTime(randlModuleDto.getCreateTime().format(dtf));
        return randlModuleDto;
    }


    @Override
    public Map<String, Object> getRandlModuleTreeMap(Long appId, Long uid) {
        return null;
    }

    @Override
    public TreeNode getTreeModule(TreeModuleParams params) {
        BeanValidator.verifyParams(params);

        //从数据库获取原始模块实体列表
        List<RandlModule> randlModuleList = getBaseMapper().selectList(new QueryWrapper<RandlModule>().eq("app_id"
                , params.getAppId()));
        List<RandlModuleDto> randlModuleDtoList = getDtoList(randlModuleList);

        //使用适配器进行TreeNode组装
        List<RandlModuleDtoOneTreeNodeAdapter> adapterList = new ArrayList<>();
        for (RandlModuleDto randlModuleDto : randlModuleDtoList) {
            adapterList.add(new RandlModuleDtoOneTreeNodeAdapter(randlModuleDto));
        }
        TreeProcessor treeProcessor = new DefaultTreeProcessor();
        TreeNode rootTreeNode = treeProcessor.processing(adapterList);

        //如果设置moduleId，则从那个节点开始
        if (params.getModuleId() != null) {
            AtomicReference<TreeNode> treeNodeForModule = new AtomicReference<>(rootTreeNode);
            rootTreeNode.foreach(treeNode -> {
                if (treeNode.getValue() != null) {
                    RandlModuleDto randlModuleDto = (RandlModuleDto) treeNode.getValue();
                    if (randlModuleDto.getFid().equals(params.getModuleId())) {
                        treeNodeForModule.set(treeNode);
                        return false;
                    }
                }
                return true;
            });

            return treeNodeForModule.get();
        }
        return rootTreeNode;
    }

    @Override
    public Map<String, Object> getTreeModuleList(TreeModuleParams params) {
        return TreeNodeUtils.toTreeMap(getTreeModule(params));
    }


    @Override
    public String add(AddRandlModuleParams params) {
        BeanValidator.verifyParams(params);

        Integer defaultModuleLevel = null;

        if (params.getAppId() != null) {
            if (randlAppMapper.selectById(params.getAppId()) == null) {
                throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
            }
        }

        if (params.getParentId() != null) {
            RandlModule parentRandlModule = getBaseMapper().selectById(params.getParentId());
            if (parentRandlModule == null) {
                throw new ApiResponseBeanException(ErrorU.CODE_10.C, "parentId", "Randl模块");
            }
            else {
                defaultModuleLevel = parentRandlModule.getLevel() + 1;
            }
        }

        RandlModule randlModule = getMapperFacade().map(params, RandlModule.class);
        randlModule.setCreateTime(LocalDateTime.now());
        randlModule.setLevel(defaultModuleLevel);

        getBaseMapper().insert(randlModule);

        return "添加成功！";
    }

    @Override
    public String update(UpdateModuleParams params) {
        if (params.getParentId() != null) {
            RandlModule parentRandlModule = getBaseMapper().selectById(params.getParentId());
            if (parentRandlModule == null) {
                throw new ApiResponseBeanException(ErrorU.CODE_10.C, "parentId", "Randl模块");
            }
            else {
                params.setLevel(parentRandlModule.getLevel() + 1);
            }
        }

        return updateByParams(params);
    }

    @Override
    public String removeChainById(Long fid) {

        removeModuleFromParentId(fid);

        return "删除该模块和其下的子模块成功！";
    }

    @Override
    public PageDto<RandlModuleDto> pageByConditionTwo(QueryByPageParams queryByPageParams,
                                                      BaseConditionParams params,
                                                      QueryWrapper<RandlModule> queryWrapper) {

        ConditionModuleParams paramsForModule = (ConditionModuleParams) params;

        queryWrapper.nested(warpper -> warpper.eq("app_id", paramsForModule.getAppId()));

        return super.pageByConditionTwo(queryByPageParams, params, queryWrapper);
    }


    //**********************

    private void removeModuleFromParentId(Long fid) {
        RandlModule randlModule = getBaseMapper().selectById(fid);
        if (randlModule != null) {
            List<RandlModule> childRandlModuleList = getBaseMapper().selectList(new QueryWrapper<RandlModule>().eq(
                    "parent_id", randlModule.getFid()));
            for (RandlModule childRandlModule : childRandlModuleList) {
                removeChainById(childRandlModule.getFid());
            }

            getBaseMapper().deleteById(fid);
        }
    }

}
