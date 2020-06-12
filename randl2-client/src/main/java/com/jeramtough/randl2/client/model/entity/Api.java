package com.jeramtough.randl2.client.model.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public class Api implements Serializable {

    private static final long serialVersionUID=1L;

    private Long fid;

    private String path;

    private String alias;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "ApiDto{" +
        "fid=" + fid +
        ", path=" + path +
        ", description=" + description +
        "}";
    }
}
