package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.adminapp.service.RandlAppService;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Api(tags = {"APP应用接口"})
@RestController
@RequestMapping("/randlApp")
public class RandlAppController extends BaseController {

    private final RandlAppService randlAppService;

    @Autowired
    public RandlAppController(RandlAppService randlAppService) {
        this.randlAppService = randlAppService;
    }

    @ApiOperation(value = "新增", notes = "新增RandlApp应用")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> add(@RequestBody AddAppParams params) {
        return getSuccessfulApiResponse(randlAppService.add(params));
    }

    @ApiOperation(value = "删除", notes = "删除RandlApp应用")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlAppService.removeOneById(fid));
    }

    @ApiOperation(value = "更新", notes = "更新RandlApp应用")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> update(@RequestBody UpdateAppParams params) {
        return getSuccessfulApiResponse(randlAppService.update(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个RandlApp应用")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<RandlAppDto> getOne(Long fid) {
        return getSuccessfulApiResponse(randlAppService.getBaseDtoById(fid));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部RandlApp应用")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlAppDto>> getAllApi() {
        return getSuccessfulApiResponse(randlAppService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询API信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlAppDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlAppService.getBaseDtoListByPage(queryByPageParams));
    }


    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个api接口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {
    })
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlAppDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(randlAppService.getListByKeyword(keyword));
    }

}

