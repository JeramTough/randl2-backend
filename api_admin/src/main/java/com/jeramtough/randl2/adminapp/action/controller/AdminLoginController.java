package com.jeramtough.randl2.adminapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.optlog.annotation.IgnoreOptLog;
import com.jeramtough.randl2.adminapp.service.LoginService;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Created on 2020/10/3 10:09
 * by @author WeiBoWen
 * </pre>
 */
@RestController
@Tag(name = "管理端登录接口")
@RequestMapping(AdminLoginController.BASE_URI)
public class AdminLoginController extends MyBaseController {

    public static final String BASE_URI = "/access";

    public static final String LOGOUT_URI = "/logout";
    public static final String LOGOUT_SUCCESSFUL_URI = "/logoutSuccessful";

    private final LoginService loginService;


    @Autowired
    public AdminLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @IgnoreOptLog(isIgnoreMethod = false, isIgnoreArgs = true, isIgnoreResponse = true)
    @Operation(summary = "登录", description = "系统管理员登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @PutMapping
    @Parameters({
            @Parameter(name = "username", description = "用户名",
                    required = true, example = "superadmin"),
            @Parameter(name = "password", description = "密码",
                    required = true, example = "superadmin")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_301.C, description = ErrorU.CODE_301.M),
            @ApiResponse(responseCode = ErrorU.CODE_302.C, description = ErrorU.CODE_302.M),
            @ApiResponse(responseCode = ErrorU.CODE_303.C, description = ErrorU.CODE_303.M),
            @ApiResponse(responseCode = ErrorU.CODE_304.C, description = ErrorU.CODE_304.M),
    })
    public CommonApiResponse<SystemUserDto> adminLogin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        UserCredentials userCredentials = new UserCredentials(username,
                password);
        return getSuccessfulApiResponse(loginService.adminLoginSuccessful());
    }

    @Operation(summary = "退出登录", description = "系统管理员退出登录")
    @RequestMapping(value = LOGOUT_URI, method = {RequestMethod.POST})
    public void logout() {
        //主要逻辑在LogoutFilter过滤器里
    }

    @Operation(summary = "退出登录成功", description = "系统管理员退出登录成功")
    @RequestMapping(value = LOGOUT_SUCCESSFUL_URI,
            method = {RequestMethod.POST, RequestMethod.GET})
    public CommonApiResponse<String> logoutSuccessful() {
        return getSuccessfulApiResponse(loginService.adminLogout());
    }

}
