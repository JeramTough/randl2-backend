package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@Api(tags = {"Oauth2资源服务接口"})
@RestController
@RequestMapping("/oauthResourceDetails")
public class OauthResourceDetailsController extends MyBaseController {

    private final OauthResourceDetailsService oauthResourceDetailsService;

    @Autowired
    public OauthResourceDetailsController(
            OauthResourceDetailsService oauthResourceDetailsService) {
        this.oauthResourceDetailsService = oauthResourceDetailsService;
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<OauthResourceDetailsDto>> getAll() {
        return getSuccessfulApiResponse(oauthResourceDetailsService.getAllBaseDto());
    }

}

