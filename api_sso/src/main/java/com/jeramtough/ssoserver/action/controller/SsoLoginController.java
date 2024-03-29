package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.randl2.common.model.params.login.SsoLoginByVerificationCodeParams;
import com.jeramtough.ssoserver.service.SsoLoginService;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByPasswordParams;
import io.swagger.annotations.*;
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
@Api(tags = {"SSO登录接口"})
@RestController
@RequestMapping("/ssoLogin")
public class SsoLoginController extends MyBaseController {

    private final SsoLoginService ssoLoginService;

    public SsoLoginController(SsoLoginService ssoLoginService) {
        this.ssoLoginService = ssoLoginService;
    }

    @ApiOperation(value = "账号密码登录", notes = "使用账号密码登录")
    @RequestMapping(value = "/loginByPassword", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_301.C, message = ErrorU.CODE_301.M),
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
            @ApiResponse(code = ErrorU.CODE_303.C, message = ErrorU.CODE_303.M),
            @ApiResponse(code = ErrorU.CODE_304.C, message = ErrorU.CODE_304.M),
    })
    public CommonApiResponse<Map<String, Object>> loginByPassword(SsoLoginByPasswordParams params) {
        return getSuccessfulApiResponse(ssoLoginService.loginByPassword(params));
    }

    @ApiOperation(value = "验证码登录", notes = "使用手机或者邮箱验证码登录")
    @RequestMapping(value = "/loginByVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
            @ApiResponse(code = ErrorU.CODE_303.C, message = ErrorU.CODE_303.M),
            @ApiResponse(code = ErrorU.CODE_304.C, message = ErrorU.CODE_304.M),
            @ApiResponse(code = ErrorU.CODE_401.C, message = ErrorU.CODE_401.M),
            @ApiResponse(code = ErrorU.CODE_404.C, message = ErrorU.CODE_404.M),
    })
    public CommonApiResponse<Map<String, Object>> loginByVerificationCode(SsoLoginByVerificationCodeParams params) {
        return getSuccessfulApiResponse(ssoLoginService.loginByVerificationCode(params));
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public CommonApiResponse<String> logout() {
        return getSuccessfulApiResponse(ssoLoginService.logout());
    }

}
