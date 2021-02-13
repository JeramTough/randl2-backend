package com.jeramtough.randl2.common.component.attestation.clientdetail;

import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2020/11/16 10:38
 * by @author WeiBoWen
 * </pre>
 */
public class MyClientDetails extends BaseClientDetails {

    private static final long serialVersionUID = -9076737364565791938L;

    private final OauthClientDetails oauthClientDetails;
    private final List<OauthResourceDetails> oauthResourceDetailsList;
    private final List<OauthScopeDetails> oauthScopeDetailsList;

    public MyClientDetails(OauthClientDetails oauthClientDetails,
                           List<OauthResourceDetails> oauthResourceDetailsList,
                           List<OauthScopeDetails> oauthScopeDetailsList) {
        this.oauthClientDetails = oauthClientDetails;
        this.oauthResourceDetailsList = oauthResourceDetailsList;
        this.oauthScopeDetailsList = oauthScopeDetailsList;
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
        return oauthScopeDetailsList.size() > 0;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scopes = oauthScopeDetailsList
                .parallelStream()
                .map(OauthScopeDetails::getScopeExpression)
                .collect(Collectors.toSet());
        return scopes;
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
        Map<String, Object> infoMap = new HashMap<>(2);
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
