package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.model.params.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.model.params.verificationcode.VerifyVerificationCodeParams;
import com.jeramtough.randl2.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/2/16 15:29
 * by @author JeramTough
 * </pre>
 */
@RestController
@Api(tags = {"验证码接口"})
@RequestMapping("/verificationCode")
public class VerificationCodeController extends BaseController {

    private final VerificationCodeService verificationCodeService;

    @Autowired
    public VerificationCodeController(
            VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @ApiOperation(value = "发送验证码", notes = "发送手机或邮箱验证码")
    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 8000, message = "发送频率限制，距离下次可发送验证码还有%s秒"),
            @ApiResponse(code = 8001, message = "发送失败，因为%s")
    })
    public CommonApiResponse<String> send(SendVerificationCodeParams params) {
        return getSuccessfulApiResponse(verificationCodeService.send(params));
    }


    @ApiOperation(value = "校验验证码", notes = "验收验证码是否正确")
    @RequestMapping(value = "/verify", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 8002, message = "验证码校验失败！[%s]"),
    })
    public CommonApiResponse<String> verify(VerifyVerificationCodeParams params) {
        return getSuccessfulApiResponse(verificationCodeService.verify(params));
    }
}
