package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.oauth.SubmitAuthorizeParams;
import com.jeramtough.ssoserver.service.SsoAccessService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

/**
 * <pre>
 * Created on 2021/1/20 16:34
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "申请访问接口")
@Controller
@RequestMapping("/access")
@ApiResponses(value = {
        @ApiResponse(responseCode = ErrorU.CODE_803.C, description = ErrorU.CODE_803.M),
})
public class SsoAccessController extends MyBaseController {

    private final SsoAccessService ssoAccessService;


    public SsoAccessController(SsoAccessService ssoAccessService) {
        this.ssoAccessService = ssoAccessService;
    }

    @Operation(summary = "登录页", description = "申请访问登录页")
    @RequestMapping(value = "/goLoginPage", method = {RequestMethod.GET, RequestMethod.POST})
    
    public RedirectView goLoginPage(HttpServletRequest request, @RequestParam
            Map<String, String> parameters) {

        RedirectView view=ssoAccessService.goLoginPage(parameters);
        return view;
    }


    @Operation(summary = "提交授权", description = "提交授权表单")
    @RequestMapping(value = "/submitAuthorize", method = {RequestMethod.GET, RequestMethod.POST})
    
    @ResponseBody
    public CommonApiResponse<Object> doneAuthorize(SubmitAuthorizeParams params) {
        return getSuccessfulApiResponse(ssoAccessService.getAuthorizationResult(params));
    }
}
