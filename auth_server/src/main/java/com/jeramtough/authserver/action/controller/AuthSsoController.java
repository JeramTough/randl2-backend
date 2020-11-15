package com.jeramtough.authserver.action.controller;

import com.jeramtough.authserver.service.AuthSsoService;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.LoginCredentials;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/11/15 0:41
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"SSO认证接口"})
@RestController
@RequestMapping("/sso")
public class AuthSsoController extends MyBaseController {

    private final AuthSsoService authSsoService;

    public AuthSsoController(AuthSsoService authSsoService) {
        this.authSsoService = authSsoService;
    }

    @LoggingOperation
    @ApiOperation(value = "登录", notes = "系统管理员登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ApiResponses(value = {
           /* @ApiResponse(code = ErrorU.CODE_301.C, message = ErrorU.CODE_301.M),
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
            @ApiResponse(code = ErrorU.CODE_304.C, message = ErrorU.CODE_304.M),*/
    })
    public CommonApiResponse<SystemUserDto> adminLogin(LoginCredentials params) {
        return getSuccessfulApiResponse(authSsoService.login(params));
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public CommonApiResponse<String> logout() {
        return getSuccessfulApiResponse(authSsoService.logout());
    }

}
