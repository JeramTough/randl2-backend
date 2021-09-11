/**
 * Copyright 2020 bejson.com
 */
package com.jeramtough.randl2.sdk.model.httpresponse;

import java.util.List;

/**
 * Auto-generated: 2020-11-24 8:38:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TokenBody {

    private AdditionalInformation additionalInformation;
    private Long expiration;
    private Boolean expired;
    private Long expiresIn;
    private RefreshToken refreshToken;
    private List<String> scope;
    private String tokenType;
    private String value;

    public void setAdditionalInformation(AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean getExpired() {
        return expired;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * value 就是 token
     * @return token
     */
    public String getToken() {
        return value;
    }

}