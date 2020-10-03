package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Api(tags = {"模块接口"})
@RestController
@RequestMapping("/randlModule")
public class RandlModuleController extends BaseController {

    private final RandlModuleService randlModuleService;

    @Autowired
    public RandlModuleController(RandlModuleService randlModuleService) {
        this.randlModuleService = randlModuleService;
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统菜单")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlModuleDto>> getAll() {
        return getSuccessfulApiResponse(randlModuleService.getAllBaseDto());
    }

}

