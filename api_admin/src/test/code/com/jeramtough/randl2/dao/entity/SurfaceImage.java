package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@Schema(description="SurfaceImage对象")
public class SurfaceImage implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private String surfaceImageUrl;

    private Long uid;

    private String userType;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getSurfaceImageUrl() {
        return surfaceImageUrl;
    }

    public void setSurfaceImageUrl(String surfaceImageUrl) {
        this.surfaceImageUrl = surfaceImageUrl;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "SourceSurfaceImage{" +
        "fid=" + fid +
        ", surfaceImageUrl=" + surfaceImageUrl +
        ", uid=" + uid +
        ", userType=" + userType +
        "}";
    }
}
