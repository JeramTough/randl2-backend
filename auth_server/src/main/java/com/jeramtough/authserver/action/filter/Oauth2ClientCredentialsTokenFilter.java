package com.jeramtough.authserver.action.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.authserver.component.attestation.token.ClientSecretAuthenticationToken;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.oauth.OauthTokenParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * A filter and authentication endpoint for the OAuth2 Token Endpoint. Allows clients to authenticate using request
 * parameters if included as a security filter, as permitted by the specification (but not recommended). It is
 * recommended by the specification that you permit HTTP basic authentication for clients, and not use this filter at
 * all.
 *
 * @author Dave Syer
 * <p>
 * <p>
 * /*
 * Copyright 2006-2011 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

/**
 * <pre>
 * Created on 2020/11/17 22:32
 * by @author WeiBoWen
 * </pre>
 */
@ApiResponses(value = {
        @ApiResponse(code = ErrorU.CODE_802.C, message = ErrorU.CODE_802.M),
})
public class Oauth2ClientCredentialsTokenFilter extends BaseCredentialsTokenFilter {


    private final AuthenticationManager authenticationManager;


    public Oauth2ClientCredentialsTokenFilter(
            AuthenticationManager authenticationManager) {
        this("/oauthV2/token", authenticationManager);
    }

    public Oauth2ClientCredentialsTokenFilter(String path,
                                              AuthenticationManager authenticationManager) {
        super(path);
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(
                new Oauth2ClientCredentialsTokenFilter.ClientCredentialsRequestMatcher(
                        path));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        OauthTokenParams params = new OauthTokenParams(request);
        try {
            BeanValidator.verifyParams(params);
        }
        catch (ApiResponseException e) {
            returnCommonApiResponse(getFailedApiResponse(e), response);
        }


        // If the request is already authenticated we can assume that this
        // filter is not needed
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }

        String clientId = params.getClientId().trim();
        ClientSecretAuthenticationToken authRequest = new ClientSecretAuthenticationToken(clientId,
                params.getClientSecret());

        //使用authenticationManager里的AuthenticationProvider进行校验
        Authentication authenticationHasVerified = null;
        try {
            authenticationHasVerified = authenticationManager.authenticate(authRequest);
        }
        catch (Exception e) {
            returnCommonApiResponse(getFailedApiResponse(e), response);
        }

        return authenticationHasVerified;
    }


    /**
     * 授权模式匹配路径
     */
    protected static class ClientCredentialsRequestMatcher implements RequestMatcher {

        private String path;

        public ClientCredentialsRequestMatcher(String path) {
            this.path = path;

        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String uri = request.getRequestURI();
            int pathParamIndex = uri.indexOf(';');

            if (pathParamIndex > 0) {
                // strip everything after the first semi-colon
                uri = uri.substring(0, pathParamIndex);
            }

            String clientId = request.getParameter(OAuth2Constants.CLIENT_ID);
            if (clientId == null) {
                clientId = request.getParameter(OAuth2Constants.CLIENT_ID_2);
            }

            if (clientId == null) {
                // Give basic auth a chance to work instead (it's preferred anyway)
                return false;
            }

            if ("".equals(request.getContextPath())) {
                return uri.endsWith(path);
            }

            return uri.endsWith(request.getContextPath() + path);
        }

    }

}

