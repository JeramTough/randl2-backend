package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.service.VerificationCodeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RestfulApiResponse send(
            @RequestBody SendVerificationCodeParams params) {
        return getSuccessfulApiResponse(verificationCodeService.send(params));
    }

    @ApiOperation(value = "发送验证码", notes = "发送6位数的手机或邮箱验证码")
    @RequestMapping(value = "/verify", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query",
                    required = true, dataType = "String", defaultValue = "123456")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 8002, message = "校验失败！未发送或校验码错误"),
    })
    public RestfulApiResponse verify(@RequestParam String code) {
        return getSuccessfulApiResponse(verificationCodeService.verify(code));
    }
}
