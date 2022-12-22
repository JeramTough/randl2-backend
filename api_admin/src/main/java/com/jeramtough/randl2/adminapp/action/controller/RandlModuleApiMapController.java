package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.params.module.SetModuleApiMapParams;
import com.jeramtough.randl2.service.randl.RandlModuleApiMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Api(tags = {"Randl模块与接口映射关系接口"})
@RestController
@RequestMapping("/randlModuleApiMap")
public class RandlModuleApiMapController extends MyBaseController {

    private final RandlModuleApiMapService randlModuleApiMapService;

    @Autowired
    public RandlModuleApiMapController(
            RandlModuleApiMapService randlModuleApiMapService) {
        this.randlModuleApiMapService = randlModuleApiMapService;
    }


    @RegApi
    @ApiOperation(value = "查询关系通过(1)", notes = "查询关系通过应用Id和模块Id,返回接口Id列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "moduleId", value = "模块Id", required = true, defaultValue = "1")
    })
    @RequestMapping(value = "/getMapBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<Long>> getMapByAppIdAndModuleId(@RequestParam(value = "appId") Long appId,
                                                                  @RequestParam(value = "moduleId")
                                                                          Long moduleId) {
        return getSuccessfulApiResponse(randlModuleApiMapService.getApiListByAppIdAndModuleId(appId, moduleId));
    }

    @RegApi
    @ApiOperation(value = "设置映射关系", notes = "设置模块与接口的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetModuleApiMapParams params) {
        return getSuccessfulApiResponse(randlModuleApiMapService.setModuleApiMap(params));
    }


}

