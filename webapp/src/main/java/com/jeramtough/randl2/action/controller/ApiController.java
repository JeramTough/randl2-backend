package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.service.ApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = 4010, message = "删除接口失败！该接口不存在"),
    })
    public RestfulApiResponse deleteApi(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(apiService.delete(fid));
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = 4030, message = "查询接口失败！该接口不存在"),
    })
    public RestfulApiResponse getApi(Long fid) {
        return getSuccessfulApiResponse(apiService.getApi(fid));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllApi() {
        return getSuccessfulApiResponse(apiService.getAllApi());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询API信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public RestfulApiResponse getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                apiService.getBaseDtoListByPage(queryByPageParams));
    }

}


