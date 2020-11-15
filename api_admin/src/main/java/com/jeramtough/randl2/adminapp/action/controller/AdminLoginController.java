package com.jeramtough.randl2.adminapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;

import com.jeramtough.randl2.adminapp.service.LoginService;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/10/3 10:09
 * by @author WeiBoWen
 * </pre>
 */
@RestController
@Api(tags = {"管理端登录接口"})
@RequestMapping("/access")
public class AdminLoginController extends MyBaseController {

    private final LoginService loginService;

    @Autowired
    public AdminLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @LoggingOperation
    @ApiOperation(value = "登录", notes = "系统管理员登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query",
                    required = true, dataType = "String", defaultValue = "superadmin"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",
                    required = true, dataType = "String", defaultValue = "superadmin")})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_301.C, message = ErrorU.CODE_301.M),
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
            @ApiResponse(code = ErrorU.CODE_304.C, message = ErrorU.CODE_304.M),
    })
    public CommonApiResponse<SystemUserDto> adminLogin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        UserCredentials userCredentials = new UserCredentials(username,
                password);
        return getSuccessfulApiResponse(loginService.adminLogin(userCredentials));
    }

    @LoggingOperation
    @ApiOperation(value = "退出登录", notes = "系统管理员退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public CommonApiResponse<String> logout() {
        return getSuccessfulApiResponse(loginService.adminLogout());
    }

}
