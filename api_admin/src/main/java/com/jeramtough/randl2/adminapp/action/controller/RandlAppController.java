package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.service.randl.RandlAppService;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
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
@ApiResponses({
        @ApiResponse(code = ErrorU.CODE_701.C, message = ErrorU.CODE_701.M),
})
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
    public CommonApiResponse<List<RandlAppDto>> getAll() {
        return getSuccessfulApiResponse(randlAppService.getAllBaseDto());
    }

    @ApiOperation(value = "查询全部但只有name属性", notes = "查询全部RandlApp应用，但只有name属性")
    @RequestMapping(value = "/allOnlyName", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlAppDto>> getAllOnlyName() {
        return getSuccessfulApiResponse(randlAppService.getAllOnlyName());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询APP信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlAppDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlAppService.getBaseDtoListByPage(queryByPageParams));
    }


    @ApiOperation(value = "条件查询", notes = "根据关键字等条件查询得到一个Randl应用信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlAppDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionAppParams params) {
        return getSuccessfulApiResponse(randlAppService.pageByCondition(queryByPageParams, params));
    }

}

