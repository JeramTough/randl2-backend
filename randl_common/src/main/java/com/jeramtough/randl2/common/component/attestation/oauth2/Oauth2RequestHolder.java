package com.jeramtough.randl2.common.component.attestation.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2021/1/23 17:25
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class Oauth2RequestHolder {

    public static final Long TIME = 60L * 60;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RandlOAuth2RequestFactory randlOauth2RequestFactory;


    @Autowired
    public Oauth2RequestHolder(
            RedisTemplate<String, Object> redisTemplate,
            RandlOAuth2RequestFactory randlOauth2RequestFactory) {
        this.redisTemplate = redisTemplate;
        this.randlOauth2RequestFactory = randlOauth2RequestFactory;
    }

    public String saveRequestParams(Map<String, String> requestParams) {
        String id = IdUtil.getUUID();
        redisTemplate.opsForValue().set(this.getClass().getSimpleName() + "_" + id,
                JSON.toJSONString(requestParams), TIME, TimeUnit.SECONDS);
        return id;
    }

    public Map<String, String> getRequestParams(String tempClientId) {
        Object value = redisTemplate.opsForValue().get(this.getClass().getSimpleName() + "_" + tempClientId);
        if (value == null) {
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(value.toString());
        Map<String, String> requestParams = new HashMap<>(16);
        jsonObject.entrySet()
                  .parallelStream()
                  .forEach(entry -> requestParams.put(entry.getKey(), entry.getValue().toString()));

        return requestParams;
    }

    public String getClientId(String tempClientId) {
        Map<String, String> params = getRequestParams(tempClientId);
        if (params == null) {
            return null;
        }
        String clientId = params.get(OAuth2Constants.CLIENT_ID);
        if (clientId == null) {
            clientId = params.get(OAuth2Constants.CLIENT_ID_2);
        }
        return clientId;
    }

    public AuthorizationRequest getAuthorizationRequest(String tempClientId, String clientScopesStr) {
        Map<String, String> params = getRequestParams(tempClientId);
        if (params == null) {
            return null;
        }
        params.put(OAuth2Constants.SCOPE, clientScopesStr);
        AuthorizationRequest authorizationRequest = randlOauth2RequestFactory.createAuthorizationRequest(params);
        return authorizationRequest;
    }

}
