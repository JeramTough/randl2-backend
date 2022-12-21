package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/15 22:10
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"开放资源接口"})
@RestController
@RequestMapping("/open")
public class OpenController extends MyBaseController {
    //http://127.0.0.1:9086/randl2/resource/user/test

    @ApiOperation(value = "测试资源", notes = "测试资源")
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public CommonApiResponse<String> getPersonalInfoByUid(HttpServletRequest request) {
        return getSuccessfulApiResponse("lalallalla");
    }

}
