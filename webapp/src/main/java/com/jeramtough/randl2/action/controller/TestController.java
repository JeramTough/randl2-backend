package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/1/26 16:00
 * by @author JeramTough
 * </pre>
 */
@RestController
@Api(tags = {"测试接口"})
@RequestMapping(value = "/test")
public class TestController extends BaseSwaggerController {

    @ApiOperation(value = "获取资源", notes = "获取某些资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数也", paramType = "query",
                    required = true, dataType = "String", defaultValue = "JeramTough")})
    @RequestMapping(value = "/getSomething", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getSomething(String param) {
        return "test-" + param;
    }

}
