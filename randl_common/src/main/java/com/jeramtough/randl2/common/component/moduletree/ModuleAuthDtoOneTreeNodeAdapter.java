package com.jeramtough.randl2.common.component.moduletree;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;

/**
 * <pre>
 *  将每一个ModuleAuthDto适配为TreeNode的每个节点
 * Created on 2020/10/4 17:37
 * by @author WeiBoWen
 * </pre>
 */
public class ModuleAuthDtoOneTreeNodeAdapter implements OneTreeNodeAdapter<RandlModuleAuthDto> {

    private final RandlModuleAuthDto moduleAuthDto;

    public ModuleAuthDtoOneTreeNodeAdapter(RandlModuleAuthDto moduleAuthDto) {
        this.moduleAuthDto = moduleAuthDto;
    }

    @Override
    public Object getKey() {
        return moduleAuthDto.getMid();
    }

    @Override
    public Object getParentKey() {
        return moduleAuthDto.getParentModuleId();
    }

    @Override
    public RandlModuleAuthDto getValue() {
        return moduleAuthDto;
    }

    @Override
    public int getOrder() {
        if (moduleAuthDto.getModuleOrder() != null) {
            return moduleAuthDto.getModuleOrder();
        }
        return 0;
    }
}
