package com.jeramtough.authserver.action.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/3/18 16:41
 * by @author JeramTough
 * </pre>
 */
@Api(tags = {"Test测试接口"})
@RestController
@RequestMapping("/test")
public class TestController {


    @ApiOperation(value = "测试登录成功否", notes = "测试登录成功否")
    @RequestMapping(value = "/testLogined", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @ApiOperation(value = "测试登录成功否2", notes = "测试登录成功否")
    @RequestMapping(value = "/testLogined2", method = RequestMethod.GET)
    public String test2() {
        return "test2";
    }

    @ApiOperation(value = "测试登录成功否3", notes = "测试登录成功否")
    @RequestMapping(value = "/testLogined3", method = RequestMethod.GET)
    public String test3() {
        return "test3";
    }


}
