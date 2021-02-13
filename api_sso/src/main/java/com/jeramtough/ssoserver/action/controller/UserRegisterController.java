package com.jeramtough.ssoserver.action.controller;


import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.service.user.UserRegisterService;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@Api(tags = {"普通注册用户接口"})
@RequestMapping("/registerUser")
@ApiResponses(value = {
        @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
})
public class UserRegisterController extends MyBaseController {

    private final UserRegisterService userRegisterService;


    @Autowired
    public UserRegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @ApiOperation(value = "新账号注册方式校验", notes = "校验手机号码或者邮箱是否允许被注册")
    @RequestMapping(value = "/verify/phoneOrEmailForNew", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailForNew(
            @RequestBody VerifyPhoneOrEmailForNewParams params) {
        return getSuccessfulApiResponse(
                userRegisterService.verifyPhoneOrEmailForNew(params));
    }

    @ApiOperation(value = "找回密码账号校验", notes = "校验手机号码或者邮箱")
    @RequestMapping(value = "/verify/phoneOrEmailByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailByForget(@RequestBody
                                                                                     VerifyPhoneOrEmailByForgetParams params) {
        return getSuccessfulApiResponse(
                userRegisterService.verifyPhoneOrEmailByForget(params));
    }

    @ApiOperation(value = "注册时用户密码校验", notes = "注册时用户密码校验")
    @RequestMapping(value = "/verify/password", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> verifyPassword(@RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(userRegisterService.verifyPassword(params));
    }

    @ApiOperation(value = "找回密码时用户密码校验", notes = "找回密码时用户密码校验")
    @RequestMapping(value = "/verify/passwordByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<Object> verifyPasswordByForget(
            @RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(userRegisterService.verifyPasswordByForget(params));
    }

    @ApiOperation(value = "确定注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_202.C, message = ErrorU.CODE_202.M),
            @ApiResponse(code = ErrorU.CODE_401.C, message = ErrorU.CODE_401.M),
    })
    public CommonApiResponse<Object> registerUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(userRegisterService.register(params));
    }

    @ApiOperation(value = "确定重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_203.C, message = ErrorU.CODE_203.M),
            @ApiResponse(code = ErrorU.CODE_401.C, message = ErrorU.CODE_401.M),
    })
    public CommonApiResponse<Object> resetUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(userRegisterService.reset(params));
    }


}

