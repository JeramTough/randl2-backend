package com.jeramtough.randl2.common.model.entity;

import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value="OauthCode对象", description="")
public class OauthCode implements Serializable {

    private static final long serialVersionUID=1L;

    private String code;

    private Blob authentication;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return "OauthCode{" +
        "code=" + code +
        ", authentication=" + authentication +
        "}";
    }
}
