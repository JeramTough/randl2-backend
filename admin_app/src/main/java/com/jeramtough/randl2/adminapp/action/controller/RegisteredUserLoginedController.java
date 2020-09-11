package com.jeramtough.randl2.adminapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.params.registereduser.*;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.common.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.adminapp.service.RegisteredUserLoginService;
import com.jeramtough.randl2.adminapp.service.RegisteredUserLoginedService;
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
 * <pre>
 * Created on 2020/3/22 23:00
 * by @author JeramTough
 * </pre>
 */
@RestController
@Api(tags = {"普通注册用户登录后接口"})
@RequestMapping("/registeredUser")
@ApiResponses({
        @ApiResponse(code = ErrorU.CODE_3.C, message = ErrorU.CODE_3.M),
        @ApiResponse(code = ErrorU.CODE_4.C, message = ErrorU.CODE_4.M),

})
public class RegisteredUserLoginedController extends BaseController {

    private final RegisteredUserLoginService registeredUserLoginService;
    private final RegisteredUserLoginedService registeredUserLoginedService;

    @Autowired
    public RegisteredUserLoginedController(
            RegisteredUserLoginService registeredUserLoginService,
            RegisteredUserLoginedService registeredUserLoginedService) {
        this.registeredUserLoginService = registeredUserLoginService;
        this.registeredUserLoginedService = registeredUserLoginedService;
    }

    @ApiOperation(value = "游客登录", notes = "游客登录，授予游客身份令牌")
    @RequestMapping(value = "/login/visitor", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_302.C, message = ErrorU.CODE_302.M),
    })
    public CommonApiResponse<Map<String, Object>> loginForVisitor(
            LoginForVisitorCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginForVisitor(credentials));
    }

    @ApiOperation(value = "登录1", notes = "普通注册用户通过密码登录")
    @RequestMapping(value = "/login/byPassword", method = {RequestMethod.POST})
    public CommonApiResponse<Map<String, Object>> login1(
            LoginByPasswordCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByPassword(credentials));
    }


    @ApiOperation(value = "登录2", notes = "普通注册用户通过验证码登录")
    @RequestMapping(value = "/login/byVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_303.C, message = ErrorU.CODE_303.M)
    })
    public CommonApiResponse<Map<String, Object>> login2(
            LoginByVerificationCodeCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByVerificationCode(credentials));
    }

    @ApiOperation(value = "个人资料", notes = "查询当前登录账号的个人资料")
    @RequestMapping(value = "/logined/personalInfo", method = {RequestMethod.GET})
    @ApiResponses(value = {
    })
    public CommonApiResponse<PersonalInfoDto> getPersonalInfoByUid() {
        return getSuccessfulApiResponse(registeredUserLoginedService.getPersonalInfo());
    }

    @ApiOperation(value = "更新个人资料", notes = "更新当前登录用户个人信息")
    @RequestMapping(value = "/logined/updatePersonalInfo", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateAdminUser(
            @RequestBody UpdatePersonalInfoParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.updatePersonalInfo(params));
    }

    @ApiOperation(value = "重置密码", notes = "重置当前登录用户的密码")
    @RequestMapping(value = "/logined/resetPassword", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_206.C, message = ErrorU.CODE_206.M),
    })
    public CommonApiResponse<String> resetPassword(
            @RequestBody ResetPasswordParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.resetPassword(params));
    }

    @ApiOperation(value = "更新头像", notes = "更新普通用户的头像")
    @RequestMapping(value = "/logined/updateSurfaceImage", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateUserSurfaceImageByBase64(
            @RequestBody UploadSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.updateSurfaceImageByBase64(params));
    }


    @ApiOperation(value = "绑定", notes = "绑定账号的手机号码或者邮箱")
    @RequestMapping(value = "/logined/bindingPhoneOrEmail", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_207.C, message = ErrorU.CODE_207.M),
            @ApiResponse(code = ErrorU.CODE_208.C, message = ErrorU.CODE_208.M),
    })
    public CommonApiResponse<String> bindingPhoneOrEmail(
            @RequestBody BindingPhoneOrEmailParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.bindPhoneNumberOrEmailAddress(params));
    }

}
