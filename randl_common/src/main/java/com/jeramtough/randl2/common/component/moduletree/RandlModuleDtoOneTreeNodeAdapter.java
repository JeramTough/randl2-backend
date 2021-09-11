package com.jeramtough.randl2.common.component.moduletree;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.entity.RandlModule;

import java.util.Date;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 *  将每一个RandlModule适配为TreeNode的每个节点
 * Created on 2020/10/4 17:37
 * by @author WeiBoWen
 * </pre>
 */
public class RandlModuleDtoOneTreeNodeAdapter implements OneTreeNodeAdapter<RandlModuleDto> {

    private final RandlModuleDto randlModuleDto;

    public RandlModuleDtoOneTreeNodeAdapter(RandlModuleDto randlModuleDto) {
        this.randlModuleDto = randlModuleDto;
    }

    @Override
    public Object getKey() {
        return randlModuleDto.getFid();
    }

    @Override
    public Object getParentKey() {
        return randlModuleDto.getParentId();
    }

    @Override
    public RandlModuleDto getValue() {
        return randlModuleDto;
    }

    @Override
    public int getOrder() {
        if (randlModuleDto.getModuleOrder() != null) {
            return randlModuleDto.getModuleOrder();
        }
        return 0;
    }
}
