package com.jeramtough.randl2.common.model.dto;

/**
 * <pre>
 * Created on 2020/11/21 20:50
 * by @author WeiBoWen
 * </pre>
 */
public class OauthTokenDto {

    private String tokenPrefix;

    private String value;

    private Long expiration;

    private String info;

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
