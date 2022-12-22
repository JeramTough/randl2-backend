package com.jeramtough.ssoserver.action.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/3/18 16:41
 * by @author JeramTough
 * </pre>
 */
@Tag(name = "Test测试接口")
@RestController
@RequestMapping("/test")
public class TestController {


    @Operation(summary = "登录成功否", description = "刷新令牌登录成功否")
    @RequestMapping(value = "/testLogined", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @Operation(summary = "测试登录成功否2", description = "测试登录成功否")
    @RequestMapping(value = "/testLogined2", method = RequestMethod.GET)
    public String test2() {
        return "test2";
    }

    @Operation(summary = "测试登录成功否3", description = "测试登录成功否")
    @RequestMapping(value = "/testLogined3", method = RequestMethod.GET)
    public String test3() {
        return "test3";
    }


}
