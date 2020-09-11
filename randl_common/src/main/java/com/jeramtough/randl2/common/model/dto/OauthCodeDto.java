package com.jeramtough.randl2.common.model.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@ApiModel(value = "OauthCodeDTO对象", description = "")
public class OauthCodeDto implements Serializable{

private static final long serialVersionUID=1L;

        private String code;

        private Blob authentication;


    public String getCode(){
            return code;
            }

        public void setCode(String code) {
            this.code = code;
            }

    public Blob getAuthentication(){
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
