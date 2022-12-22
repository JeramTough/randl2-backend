package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.verificationcode.ConsumeVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.SendVerificationCodeParams;
import com.jeramtough.randl2.service.resource.VerificationCodeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "验证码接口")
@RequestMapping("/api/verificationCode")
public class VerificationCodeController extends MyBaseController {

    private final VerificationCodeService verificationCodeService;

    @Autowired
    public VerificationCodeController(
            VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @Operation(summary = "生产验证码", description = "生产验证码并发送手机或邮箱验证码")
    @RequestMapping(value = "/produce", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_402.C, description = ErrorU.CODE_402.M),
            @ApiResponse(responseCode = ErrorU.CODE_403.C, description = ErrorU.CODE_403.M),
    })
    public CommonApiResponse<String> send(SendVerificationCodeParams params) {
        return getSuccessfulApiResponse(verificationCodeService.produceVerificationCode(params));
    }


    @Operation(summary = "消费验证码", description = "验收验证码是否正确")
    @RequestMapping(value = "/consume", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_404.C, description = ErrorU.CODE_404.M),
    })
    public CommonApiResponse<String> verify(ConsumeVerificationCodeParams params) {
        return getSuccessfulApiResponse(verificationCodeService.consumeVerificationCode(params));
    }
}
