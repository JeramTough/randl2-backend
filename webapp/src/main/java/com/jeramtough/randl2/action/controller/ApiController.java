package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.service.ApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"接口信息的接口"})
public class ApiController extends BaseSwaggerController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @ApiOperation(value = "新增", notes = "新增系统接口")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 4000, message = "添加接口信息失败，[%s]参数不能为空"),
            @ApiResponse(code = 4001, message = "该接口已存在")
    })
    public RestfulApiResponse addApi(@RequestBody AddApiParams params) {
        return getSuccessfulApiResponse(apiService.addApi(params));
    }

    @ApiOperation(value = "删除", notes = "删除系统接口")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiResponses(value = {
            @ApiResponse(code = 4010, message = "删除接口失败！该接口不存在"),
    })
    public RestfulApiResponse deleteApi(Long apiId) {
        return getSuccessfulApiResponse(apiService.delete(apiId));
    }

    @ApiOperation(value = "更新", notes = "更新系统接口")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 4020, message = "更新接口信息失败，[%s]参数不能为空"),
            @ApiResponse(code = 4021, message = "更新接口失败！该接口不存在")
    })
    public RestfulApiResponse updateApi(@RequestBody UpdateApiParams params) {
        return getSuccessfulApiResponse(apiService.updateApi(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个系统接口")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiResponses(value = {
            @ApiResponse(code = 4030, message = "查询接口失败！该接口不存在"),
    })
    public RestfulApiResponse getApi(Long apiId) {
        return getSuccessfulApiResponse(apiService.getApi(apiId));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllApi() {
        return getSuccessfulApiResponse(apiService.getAllApi());
    }

}


