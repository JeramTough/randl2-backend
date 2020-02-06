package com.jeramtough.randl2.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@ApiModel(value="SurfaceImage对象")
public class SurfaceImage implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
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
