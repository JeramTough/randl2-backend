package com.jeramtough.ssoserver.action.filter;

import com.jeramtough.ssoserver.component.attestation.token.JwtAuthenticationToken;
import com.jeramtough.ssoserver.component.attestation.token.JwtTokenRequestParser;
import com.jeramtough.jtweb.action.filter.BaseSwaggerFilter;
import com.jeramtough.randl2.common.util.PathMatcherUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

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
public class SsoCredentialsTokenFilter extends BaseCredentialsTokenFilter implements
        BaseSwaggerFilter {

    private final static String SSO_URLS =
            "/access/submitAuthorize";

    private final AuthenticationManager authenticationManager;


    public SsoCredentialsTokenFilter(
            AuthenticationManager authenticationManager) {
        this(SSO_URLS, authenticationManager);
    }

    public SsoCredentialsTokenFilter(String path,
                                     AuthenticationManager authenticationManager) {
        super(path);
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(
                new SsoCredentialsRequestMatcher(
                        path));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String token = new JwtTokenRequestParser().parse(request);

        // If the request is already authenticated we can assume that this
        // filter is not needed
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);

        //使用authenticationManager里的AuthenticationProvider进行校验
        Authentication authenticationHasVerified = null;
        try {
            authenticationHasVerified = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e) {
            returnCommonApiResponse(getFailedApiResponse(e), response);
        }
        return authenticationHasVerified;
    }


    /**
     * 授权模式匹配路径
     */
    protected static class SsoCredentialsRequestMatcher implements RequestMatcher {

        private final String[] ssoPaths;


        public SsoCredentialsRequestMatcher(String path) {
            if (path != null) {
                ssoPaths = path.split(",");
            }
            else {
                ssoPaths = new String[0];
            }

        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String path = request.getServletPath();

            boolean isSsoUrl = PathMatcherUtil.matches(Arrays.asList(ssoPaths),
                    path);
            return isSsoUrl;
        }

    }

}

