package com.jeramtough.randl2.userapp.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.model.dto.RegisteredUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.userapp.service.RegisteredUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/registeredUser")
@ApiResponses(value = {

})
public class RegisteredUserController extends BaseSwaggerController {

    private final RegisteredUserService registeredUserService;

    @Autowired
    public RegisteredUserController(
            RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    @ApiOperation(value = "新账号注册方式校验", notes = "校验手机号码或者邮箱是否允许被注册")
    @RequestMapping(value = "/verify/phoneOrEmailForNew", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
            @ApiResponse(code = ErrorU.CODE_202.C, message = ErrorU.CODE_202.M),
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailForNew(
            @RequestBody VerifyPhoneOrEmailForNewParams params) {
        return getSuccessfulApiResponse(
                registeredUserService.verifyPhoneOrEmailForNew(params));
    }

    @ApiOperation(value = "找回密码账号校验", notes = "校验手机号码或者邮箱")
    @RequestMapping(value = "/verify/phoneOrEmailByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_205.C, message = ErrorU.CODE_205.M),
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailByForget(@RequestBody
                                                                                     VerifyPhoneOrEmailByForgetParams params) {
        return getSuccessfulApiResponse(
                registeredUserService.verifyPhoneOrEmailByForget(params));
    }

    @ApiOperation(value = "注册时用户密码校验", notes = "注册时用户密码校验")
    @RequestMapping(value = "/verify/password", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
            @ApiResponse(code = ErrorU.CODE_101.C, message = ErrorU.CODE_101.M),
    })
    public CommonApiResponse<String> verifyPassword(@RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(registeredUserService.verifyPassword(params));
    }

    @ApiOperation(value = "找回密码时用户密码校验", notes = "找回密码时用户密码校验")
    @RequestMapping(value = "/verify/passwordByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
            @ApiResponse(code = ErrorU.CODE_101.C, message = ErrorU.CODE_101.M),
            @ApiResponse(code = ErrorU.CODE_209.C, message = ErrorU.CODE_209.M),
    })
    public CommonApiResponse<Object> verifyPasswordByForget(
            @RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(registeredUserService.verifyPasswordByForget(params));
    }

    @ApiOperation(value = "确定注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
            @ApiResponse(code = ErrorU.CODE_202.C, message = ErrorU.CODE_202.M),
            @ApiResponse(code = ErrorU.CODE_401.C, message = ErrorU.CODE_401.M),
    })
    public CommonApiResponse<RegisteredUserDto> registerUser(
            @RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(registeredUserService.register(params));
    }

    @ApiOperation(value = "确定重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_201.C, message = ErrorU.CODE_201.M),
            @ApiResponse(code = ErrorU.CODE_401.C, message = ErrorU.CODE_401.M),
            @ApiResponse(code = ErrorU.CODE_203.C, message = ErrorU.CODE_203.M),
            @ApiResponse(code = ErrorU.CODE_204.C, message = ErrorU.CODE_204.M),
    })
    public CommonApiResponse<RegisteredUserDto> resetUser(
            @RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(registeredUserService.reset(params));
    }


   /* @ApiOperation(value = "查询全部", notes = "得到全部普通注册用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getAllAdminUser() {
        return getSuccessfulApiResponse(registeredUserService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询普通注册用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RegisteredUserDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                registeredUserService.getBaseDtoListByPage(queryByPageParams));
    }

    @LoggingOperation
    @ApiOperation(value = "移除", notes = "移除系统管理员账号")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "普通注册用户Id", paramType = "query",
                    required = true)})
    public CommonApiResponse<String> removeAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(registeredUserService.removeRegisteredUser(uid));
    }

    @LoggingOperation
    @ApiOperation(value = "更新", notes = "更新普通注册用户账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateRegisteredUser(
            @RequestBody UpdateRegisteredUserParams params) {
        return getSuccessfulApiResponse(registeredUserService.updateRegisteredUser(params));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个管理员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getRegisteredUsersByKeyword(
            @RequestParam String keyword) {
        return getSuccessfulApiResponse(
                registeredUserService.getRegisteredUsersByKeyword(keyword));
    }*/


}

