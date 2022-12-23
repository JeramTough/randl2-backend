package com.jeramtough.randl2.adminapp.component.attestation.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Created on 2022/12/23 下午3:57
 * by @author WeiBoWen
 * </pre>
 */
public class OpenJtAuthorizationRequest extends BaseJtAuthorizationRequest
        implements JtAuthorizationRequest {

    private static final String[] OPENED_API_URLS = {
            "/access/login",
            "/access/logout",
            "/access/logoutSuccessful",
            "/access/unlogged",
            "/access/denied",

            "/registeredUser/verify/**",
            "/registeredUser/register",
            "/registeredUser/reset",
            "/registeredUser/login/**",
            "/verificationCode/**",
//            "/test/**",
    };

    private static final String[] SWAGGER_URLS = {
            "/swagger-resources",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs-ext",
            "/doc.html",
            "/webjars",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/images/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/api-docs-ext",
            "/api-docs",
            "/swagger-resources/configuration/ui/**",
            "/swagger-resources/configuration/security"
    };

    /**
     * 静态资源
     */
    private static Set<String> staticResourceTypes = new HashSet<>();

    public OpenJtAuthorizationRequest() {
        staticResourceTypes.add(".html");
        staticResourceTypes.add(".css");
        staticResourceTypes.add(".js");
        staticResourceTypes.add(".png");
        staticResourceTypes.add(".json");
        staticResourceTypes.add(".geojson");
        staticResourceTypes.add(".jpg");
        staticResourceTypes.add(".otf");
        staticResourceTypes.add(".eot");
        staticResourceTypes.add(".svg");
        staticResourceTypes.add(".ttf");
        staticResourceTypes.add(".woff");
        staticResourceTypes.add(".gif");
        staticResourceTypes.add(".ico");
        staticResourceTypes.add(".txt");
        staticResourceTypes.add(".gzip");
        staticResourceTypes.add(".xz");
        staticResourceTypes.add(".tar.gz");
        staticResourceTypes.add(".tar.bz2");
        staticResourceTypes.add(".jar");
        staticResourceTypes.add(".war");
        staticResourceTypes.add(".7z");
        staticResourceTypes.add(".tgz");
        staticResourceTypes.add(".gz");
        staticResourceTypes.add(".map");
    }

    @Override
    public boolean is(String requestUri) {
        boolean isSwagger = Arrays.stream(SWAGGER_URLS).anyMatch(
                s -> getAntPathMatcher().match(s, requestUri));

        boolean isOpenUrls = Arrays.stream(OPENED_API_URLS).anyMatch(
                s -> getAntPathMatcher().match(s, requestUri));

        boolean isStaticResources = staticResourceTypes.parallelStream()
                                                       .anyMatch(requestUri::endsWith);

        return isSwagger || isOpenUrls || isStaticResources;

    }

    @Override
    public AuthorizationDecision decide(HttpServletRequest request) {
        return new AuthorizationDecision(true);
    }
}
