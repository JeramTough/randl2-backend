package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-26
 */
@Schema(description="RandlModuleApiMap对象")
public class RandlModuleApiMap implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long moduleId;

    private Long appId;

    private Long apiId;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return "RandlModuleApiMap{" +
        "fid=" + fid +
        ", moduleId=" + moduleId +
        ", appId=" + appId +
        ", apiId=" + apiId +
        "}";
    }
}
