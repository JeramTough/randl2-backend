package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4AuthorizationCodeGrant;
import com.jeramtough.randl2.common.model.params.oauth.SubmitAuthorizeParams;
import com.jeramtough.ssoserver.service.SsoAccessService;
import com.jeramtough.jtcomponent.http.URLBuilder;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <pre>
 * Created on 2021/1/20 16:34
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"申请访问接口"})
@Controller
@RequestMapping("/access")
@ApiResponses(value = {
        @ApiResponse(code = ErrorU.CODE_803.C, message = ErrorU.CODE_803.M),
})
public class SsoAccessController extends MyBaseController {

    private final SsoAccessService ssoAccessService;


    public SsoAccessController(SsoAccessService ssoAccessService) {
        this.ssoAccessService = ssoAccessService;
    }

    @ApiOperation(value = "登录页", notes = "申请访问登录页")
    @RequestMapping(value = "/goLoginPage", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiResponses(value = {
    })
    public RedirectView goLoginPage(HttpServletRequest request, @RequestParam
            Map<String, String> parameters) {

        RedirectView view=ssoAccessService.goLoginPage(parameters);
        return view;
    }


    @ApiOperation(value = "提交授权", notes = "提交授权表单")
    @RequestMapping(value = "/submitAuthorize", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiResponses(value = {
    })
    @ResponseBody
    public CommonApiResponse<Object> doneAuthorize(SubmitAuthorizeParams params) {
        return getSuccessfulApiResponse(ssoAccessService.getAuthorizationResult(params));
    }
}
