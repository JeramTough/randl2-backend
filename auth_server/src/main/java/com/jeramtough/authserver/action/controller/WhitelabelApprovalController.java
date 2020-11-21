package com.jeramtough.authserver.action.controller;

import com.jeramtough.authserver.component.oauth2.token.JwtTokenRequestParser;
import com.jeramtough.jtcomponent.utils.WordUtil;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controller for displaying the approval page for the authorization server.
 *
 * @author Dave Syer
 */
@Controller
@SessionAttributes("authorizationRequest")
public class WhitelabelApprovalController {

    @RequestMapping("/oauthV2/confirmAccess")
    public ModelAndView getAccessConfirmation(HttpServletRequest request) throws
            Exception {
       /* final String approvalContent = createTemplate(model, request);
        if (request.getAttribute("_csrf") != null) {
            model.put("_csrf", request.getAttribute("_csrf"));
        }*/


        //通过过滤器到这里，肯定有令牌
        String token = new JwtTokenRequestParser().parse(request);
        Objects.requireNonNull(token);

        Map<String, Object> model=new HashMap<>();
        model.put("token", token);

		/*View approvalView = new View() {
			@Override
			public String getContentType() {
				return "text/html;charset=utf-8";
			}

			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				response.setContentType(getContentType());
				response.getWriter().append(approvalContent);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
			}
		};*/

        ModelAndView mv = new ModelAndView("confirmAccessPage", model);
        mv.addObject("token",token);
        return mv;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        Map<String, Object> model = new HashMap<>();
        model.put("token", "adasfas");
        ModelAndView mv = new ModelAndView("index", model);
        return mv;
    }

    protected String createTemplate(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();

        StringBuilder builder = new StringBuilder();
        builder.append("<html>" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "     <title>haha</title>\n" +
                "</head>" +
                "<body><h1>Oauth2授权页面</h1>");
        builder.append("<p>Do you authorize 您是否授予韦某访问罗某的 \"").append(HtmlUtils.htmlEscape(clientId));
        builder.append("\" to access your protected resources 应用下的私密资源信息?</p>");
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

        String authorizeInputTemplate = "<label><input name=\"authorize\" value=\"授权呀\" " +
                "type=\"submit\"/></label></form>";

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
            builder.append("<label><input name=\"deny\" value=\"拒绝\" type=\"submit\"/></label></form>");
        }

        builder.append("</body></html>");

        String content = builder.toString();
        content = WordUtil.solveWrongChinese(content);

        return content;
    }

    private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("<ul>");
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
                model.get("scopes") : request.getAttribute("scopes"));
        for (String scope : scopes.keySet()) {
            String approved = "true".equals(scopes.get(scope)) ? " checked" : "";
            String denied = !"true".equals(scopes.get(scope)) ? " checked" : "";
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
}