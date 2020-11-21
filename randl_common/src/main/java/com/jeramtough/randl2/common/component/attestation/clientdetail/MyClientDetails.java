package com.jeramtough.randl2.common.component.attestation.clientdetail;

import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.*;

/**
 * <pre>
 * Created on 2020/11/16 10:38
 * by @author WeiBoWen
 * </pre>
 */
public class MyClientDetails extends BaseClientDetails {

    private static final long serialVersionUID = -9076737364565791938L;

    private final OauthClientDetails oauthClientDetails;

    public MyClientDetails(OauthClientDetails oauthClientDetails) {
        this.oauthClientDetails = oauthClientDetails;
        init();
    }

    void init() {
        setAccessTokenValiditySeconds(getAccessTokenValiditySeconds());
        setAdditionalInformation(getAdditionalInformation());
        setAuthorities(getAuthorities());
        setAuthorizedGrantTypes(getAuthorizedGrantTypes());
        setClientId(getClientId());
        setClientSecret(getClientSecret());
        setRefreshTokenValiditySeconds(getRefreshTokenValiditySeconds());
        setResourceIds(getResourceIds());
        setScope(getScope());
        setAutoApproveScopes(getAutoApproveScopes());
        setRegisteredRedirectUri(getRegisteredRedirectUri());
    }

    @Override
    public String getClientId() {
        return oauthClientDetails.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        String resIdsStr = oauthClientDetails.getResourceIds();
        return splitByComma(resIdsStr);
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return oauthClientDetails.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return oauthClientDetails.getScopes() != null;
    }

    @Override
    public Set<String> getScope() {
        String scopeStr = oauthClientDetails.getScopes();
        return splitByComma(scopeStr);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return splitByComma(oauthClientDetails.getAuthorizedGrantTypes());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return splitByComma(oauthClientDetails.getWebServerRedirectUris());
    }


    @Override
    public Integer getAccessTokenValiditySeconds() {
        if (oauthClientDetails.getAccessTokenValidity() == null) {
            return 0;
        }
        else {
            return Math.toIntExact(oauthClientDetails.getAccessTokenValidity());
        }
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        if (oauthClientDetails.getRefreshTokenValidity() == null) {
            return 0;
        }
        else {
            return Math.toIntExact(oauthClientDetails.getRefreshTokenValidity());
        }
    }

    @Override
    public boolean isAutoApprove(String scope) {
        //TODO 通过 oauth_approvals 表维护自动授权功能，暂时不写
        return false;
    }

    @Override
    public Set<String> getAutoApproveScopes() {
        //TODO 通过 oauth_approvals 表维护自动授权功能，暂时不写
        return new HashSet<>();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> infoMap = new HashMap<>(3);
        infoMap.put("content", oauthClientDetails.getAdditionalInformationContent());
        infoMap.put("appId", oauthClientDetails.getAppId());
        infoMap.put("fid", oauthClientDetails.getFid());

        return infoMap;
    }

    public OauthClientDetails getOauthClientDetails() {
        return oauthClientDetails;
    }

    //************************

    private Set<String> splitByComma(String str) {
        if (str != null) {
            String[] strArray = str.split(",");
            Set<String> set = new HashSet<>(strArray.length);
            Collections.addAll(set, strArray);
            return set;
        }
        else {
            return new HashSet<>();
        }
    }

}
