package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.service.randl.RandlUserRoleMapService;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.params.user.SetUserRoleMapParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Api(tags = {"Randl用户角色映射接口"})
@RestController
@RequestMapping("/randlUserRoleMap")
public class RandlUserRoleMapController extends BaseController {

    private final RandlUserRoleMapService randlUserRoleMapService;

    @Autowired
    public RandlUserRoleMapController(
            RandlUserRoleMapService randlUserRoleMapService) {
        this.randlUserRoleMapService = randlUserRoleMapService;
    }

    @ApiOperation(value = "设置映射关系", notes = "设置模块与接口的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetUserRoleMapParams params) {
        return getSuccessfulApiResponse(randlUserRoleMapService.setUserRoleMap(params));
    }


}

