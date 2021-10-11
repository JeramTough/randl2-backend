package com.jeramtough.randl2.sdk.model.randl;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
public class SourceSurfaceImage implements Serializable{

    private static final long serialVersionUID=1L;

    private Long fid;

    private String surfaceImage;

    private Integer type;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SourceSurfaceImage{" +
        "fid=" + fid +
        ", surfaceImage=" + surfaceImage +
        "}";
    }
}
