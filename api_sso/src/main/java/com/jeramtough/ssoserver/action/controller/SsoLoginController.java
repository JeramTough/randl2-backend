package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByVerificationCodeParams;
import com.jeramtough.ssoserver.service.SsoLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 0:41
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "SSO登录接口")
@RestController
@RequestMapping("/ssoLogin")
public class SsoLoginController extends MyBaseController {

    private final SsoLoginService ssoLoginService;

    public SsoLoginController(SsoLoginService ssoLoginService) {
        this.ssoLoginService = ssoLoginService;
    }

    @Operation(summary = "账号密码登录", description = "使用账号密码登录")
    @RequestMapping(value = "/loginByPassword", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_301.C, description = ErrorU.CODE_301.M),
            @ApiResponse(responseCode = ErrorU.CODE_302.C, description = ErrorU.CODE_302.M),
            @ApiResponse(responseCode = ErrorU.CODE_303.C, description = ErrorU.CODE_303.M),
            @ApiResponse(responseCode = ErrorU.CODE_304.C, description = ErrorU.CODE_304.M),
    })
    public CommonApiResponse<Map<String, Object>> loginByPassword(SsoLoginByPasswordParams params) {
        return getSuccessfulApiResponse(ssoLoginService.loginByPassword(params));
    }

    @Operation(summary = "验证码登录", description = "使用手机或者邮箱验证码登录")
    @RequestMapping(value = "/loginByVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_302.C, description = ErrorU.CODE_302.M),
            @ApiResponse(responseCode = ErrorU.CODE_303.C, description = ErrorU.CODE_303.M),
            @ApiResponse(responseCode = ErrorU.CODE_304.C, description = ErrorU.CODE_304.M),
            @ApiResponse(responseCode = ErrorU.CODE_401.C, description = ErrorU.CODE_401.M),
            @ApiResponse(responseCode = ErrorU.CODE_404.C, description = ErrorU.CODE_404.M),
    })
    public CommonApiResponse<Map<String, Object>> loginByVerificationCode(SsoLoginByVerificationCodeParams params) {
        return getSuccessfulApiResponse(ssoLoginService.loginByVerificationCode(params));
    }

    @Operation(summary = "退出登录", description = "退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public CommonApiResponse<String> logout() {
        return getSuccessfulApiResponse(ssoLoginService.logout());
    }

}
