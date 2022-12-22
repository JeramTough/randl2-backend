package com.jeramtough.randl2.adminapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2021/2/22 15:43
 * by @author WeiBoWen
 * </pre>
 */
@RestController
@Tag(name = "测试接口")
@RequestMapping("/test")
public class TestController extends MyBaseController {

    @Operation(summary = "test1", description = "test1")
    @RequestMapping(value = "/test1", method = {RequestMethod.POST})
    public CommonApiResponse<String> adminLogin(String a, HttpServletResponse response) {
        return getSuccessfulApiResponse("test1");
    }
}
