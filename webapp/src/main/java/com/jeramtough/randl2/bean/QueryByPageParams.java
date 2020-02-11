package com.jeramtough.randl2.bean;

import io.swagger.annotations.ApiParam;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/8 16:36
 * by @author JeramTough
 * </pre>
 */
public class QueryByPageParams {

    @NotNull(message = "{'code':667,'placeholders':['获取数据','第几页']}")
    @ApiParam(value = "第几页",required = true,example = "1")
    private Long index;

    @NotNull(message = "{'code':667,'placeholders':['获取数据','每页大小']}")
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
