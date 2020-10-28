package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.processor.TreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.component.moduletree.RandlModuleDtoOneTreeNodeAdapter;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;
import com.jeramtough.randl2.common.model.params.mudule.TreeModuleParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Map<String, Object> getTreeModuleList(TreeModuleParams params) {
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

        //输出
        return TreeNodeUtils.toTreeMap(rootTreeNode);
    }


    //**********************


}
