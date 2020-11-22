package com.jeramtough.randl2.userapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.userapp.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/10/3 10:09
 * by @author WeiBoWen
 * </pre>
 */
@RestController
@Api(tags = {"Randl用户登录"})
@RequestMapping("/access")
public class UserLoginController extends MyBaseController {

    private final LoginService loginService;

    @Autowired
    public UserLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "登录", notes = "Randl用户登录")
    @RequestMapping(value = "/loginByPassword", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_301.C, message = ErrorU.CODE_301.M),
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
            @ApiResponse(code = ErrorU.CODE_303.C, message = ErrorU.CODE_303.M),
            @ApiResponse(code = ErrorU.CODE_304.C, message = ErrorU.CODE_304.M),
    })
    public CommonApiResponse<SystemUserDto> userLogin(LoginByPasswordParams params) {
        return getSuccessfulApiResponse(loginService.userLogin(params));
    }

}
