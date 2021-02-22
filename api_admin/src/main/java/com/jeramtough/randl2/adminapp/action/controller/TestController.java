package com.jeramtough.randl2.adminapp.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * Created on 2021/2/22 15:43
 * by @author WeiBoWen
 * </pre>
 */
@RestController
@Api(tags = {"测试接口"})
@RequestMapping("/test")
public class TestController extends MyBaseController {

    @ApiOperation(value = "test1", notes = "test1")
    @RequestMapping(value = "/test1", method = {RequestMethod.POST})
    public CommonApiResponse<String> adminLogin(String a, HttpServletResponse response) {
        return getSuccessfulApiResponse("test1");
    }
}
