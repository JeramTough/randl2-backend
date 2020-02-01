package com.jeramtough.randl2.bean.permission;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/1 13:51
 * by @author JeramTough
 * </pre>
 */
public class UpdateApiParams {

    @ApiModelProperty(value = "接口Id", example = "1", required = true)
    @NotNull(message = "{'code':4020,'placeholders':['接口Id']}")
    private Long fid;

    private String path;

    private String description;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
