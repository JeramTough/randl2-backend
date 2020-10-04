package com.jeramtough.randl2.common.component.moduletree;

import com.jeramtough.jtcomponent.tree.adapter.OnceTreeNodeAdapter;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;

/**
 * <pre>
 * Created on 2020/10/4 17:37
 * by @author WeiBoWen
 * </pre>
 */
public class ModuleAuthDtoOnceTreeNodeAdapter implements OnceTreeNodeAdapter<RandlModuleAuthDto> {

    private final RandlModuleAuthDto moduleAuthDto;

    public ModuleAuthDtoOnceTreeNodeAdapter(RandlModuleAuthDto moduleAuthDto) {
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
