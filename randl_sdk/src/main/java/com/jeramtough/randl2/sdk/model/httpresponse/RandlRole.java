package com.jeramtough.randl2.sdk.model.httpresponse;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
public class RandlRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fid;

    private String name;

    private String description;

    private Long appId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String alias;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "RandlRole{" +
                "fid=" + fid +
                ", name=" + name +
                ", description=" + description +
                ", appId=" + appId +
                ", createTime=" + createTime +
                ", aliasName=" + alias +
                "}";
    }
}
