package com.jeramtough.randl2.client.model.entity;


import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
public class SurfaceImage implements Serializable {

    private static final long serialVersionUID=1L;

    private Long fid;

    private String surfaceImage;


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


    @Override
    public String toString() {
        return "SurfaceImage{" +
        "fid=" + fid +
        ", surfaceImage=" + surfaceImage +
        "}";
    }
}
