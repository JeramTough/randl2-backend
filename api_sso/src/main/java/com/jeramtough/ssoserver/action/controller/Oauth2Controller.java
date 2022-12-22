package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/17 11:08
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "OauthV2接口")
@RestController
@RequestMapping("/oauthV2")
@ApiResponses(value = {
        @ApiResponse(responseCode = ErrorU.CODE_802.C, description = ErrorU.CODE_802.M),
        @ApiResponse(responseCode = ErrorU.CODE_804.C, description = ErrorU.CODE_804.M),
})
public class Oauth2Controller extends AbstractOauthController implements WithLogger {


    @Autowired
    public Oauth2Controller(
            @Qualifier("myTokenGranter")
                    TokenGranter tokenGranter,
            ClientDetailsService clientDetailsService,
            AuthorizationCodeServices authorizationCodeServices,
            AuthorizationServerTokenServices tokenServices,
            @Qualifier("randlOAuth2RequestFactory")
                    OAuth2RequestFactory oAuth2RequestFactory,
            @Qualifier("authenticationManager")
                    AuthenticationManager authenticationManager) {
        super(tokenGranter, clientDetailsService, tokenServices, authorizationCodeServices, authenticationManager,
                oAuth2RequestFactory);
    }

    @Operation(summary = "token令牌", description = "申请token令牌")
    @RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_802.C, description = ErrorU.CODE_802.M),
    })
    public CommonApiResponse<OAuth2AccessToken> getAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters) {

        if (!(principal instanceof Authentication)) {
            throw new InsufficientAuthenticationException(
                    "There is no client authentication. Try adding an appropriate authentication filter.");
        }


        String clientId = getClientId(principal);
//        String clientId = authenticationToken.getClientId();

        ClientDetails clientDetails = getClientDetailsService().loadClientByClientId(clientId);

        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, clientDetails);


        if (clientDetails != null) {
            getOAuth2RequestValidator().validateScope(tokenRequest, clientDetails);
        }
        if (!StringUtils.hasText(tokenRequest.getGrantType())) {
            throw new InvalidRequestException("Missing grant type");
        }
        if (tokenRequest.getGrantType().equals("implicit")) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }

        if (isAuthCodeRequest(parameters)) {
            // The scope was requested or determined during the authorization step
            if (!tokenRequest.getScope().isEmpty()) {
                getLogger().debug("Clearing scope of incoming token request");
                tokenRequest.setScope(Collections.<String>emptySet());
            }
        }

        if (isRefreshTokenRequest(parameters)) {
            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
            tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
        }

        OAuth2AccessToken token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type");
        }

        return getSuccessfulApiResponse(token);

    }


    protected String createTemplate(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();

        StringBuilder builder = new StringBuilder();
        builder.append("<html><body><h1>OAuth Approval</h1>");
        builder.append("<p>Do you authorize 您是否授予 \"").append(HtmlUtils.htmlEscape(clientId));
        builder.append("\" to access your protected resources 访问您的私密资源信息?</p>");
        builder.append("<form id=\"confirmationForm\" name=\"confirmationForm\" action=\"");

        String requestPath = ServletUriComponentsBuilder.fromContextPath(request).build().getPath();
        if (requestPath == null) {
            requestPath = "";
        }

        builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
        builder.append("<input name=\"user_oauth_approval\" value=\"true\" type=\"hidden\"/>");

        String csrfTemplate = null;
        CsrfToken csrfToken = (CsrfToken) (model.containsKey("_csrf") ? model.get("_csrf") : request.getAttribute(
                "_csrf"));
        if (csrfToken != null) {
            csrfTemplate = "<input type=\"hidden\" name=\"" + HtmlUtils.htmlEscape(csrfToken.getParameterName()) +
                    "\" value=\"" + HtmlUtils.htmlEscape(csrfToken.getToken()) + "\" />";
        }
        if (csrfTemplate != null) {
            builder.append(csrfTemplate);
        }

        String authorizeInputTemplate = "<label><input name=\"authorize\" value=\"Authorize\" type=\"submit\"/></label></form>";

        if (model.containsKey("scopes") || request.getAttribute("scopes") != null) {
            builder.append(createScopes(model, request));
            builder.append(authorizeInputTemplate);
        }
        else {
            builder.append(authorizeInputTemplate);
            builder.append("<form id=\"denialForm\" name=\"denialForm\" action=\"");
            builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
            builder.append("<input name=\"user_oauth_approval\" value=\"false\" type=\"hidden\"/>");
            if (csrfTemplate != null) {
                builder.append(csrfTemplate);
            }
            builder.append("<label><input name=\"deny\" value=\"Deny\" type=\"submit\"/></label></form>");
        }

        builder.append("</body></html>");

        return builder.toString();
    }

    //***************************

    private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("<ul>");
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
                model.get("scopes") : request.getAttribute("scopes"));
        for (String scope : scopes.keySet()) {
            String approved = "true" .equals(scopes.get(scope)) ? " checked" : "";
            String denied = !"true" .equals(scopes.get(scope)) ? " checked" : "";
            scope = HtmlUtils.htmlEscape(scope);

            builder.append("<li><div class=\"form-group\">");
            builder.append(scope).append(": <input type=\"radio\" name=\"");
            builder.append(scope).append("\" value=\"true\"").append(approved).append(">Approve</input> ");
            builder.append("<input type=\"radio\" name=\"").append(scope).append("\" value=\"false\"");
            builder.append(denied).append(">Deny</input></div></li>");
        }
        builder.append("</ul>");
        return builder.toString();
    }

    /**
     * @param principal the currently authentication principal
     * @return a client id if there is one in the principal
     */
    private String getClientId(Principal principal) {
        Authentication client = (Authentication) principal;
        if (!client.isAuthenticated()) {
            throw new InsufficientAuthenticationException("The client is not authenticated.");
        }
        String clientId = client.getName();
        if (client instanceof OAuth2Authentication) {
            // Might be a client and user combined authentication
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }
        return clientId;
    }

    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token" .equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code" .equals(parameters.get("grant_type")) && parameters.get("code") != null;
    }
}
