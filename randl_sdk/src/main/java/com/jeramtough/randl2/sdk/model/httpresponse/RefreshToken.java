/**
 * Copyright 2020 bejson.com
 */
package com.jeramtough.randl2.sdk.model.httpresponse;

/**
 * Auto-generated: 2020-11-24 8:38:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RefreshToken {

    private Long expiration;
    private String value;

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}