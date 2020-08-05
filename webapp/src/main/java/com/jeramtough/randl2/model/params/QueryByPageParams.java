package com.jeramtough.randl2.model.params;

import com.jeramtough.randl2.model.error.ErrorU;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/8 16:36
 * by @author JeramTough
 * </pre>
 */
public class QueryByPageParams {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiParam(value = "第几页",required = true,example = "1")
    private Long index;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiParam(value = "每页的大小",required = true,example = "10")
    private Long size;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
