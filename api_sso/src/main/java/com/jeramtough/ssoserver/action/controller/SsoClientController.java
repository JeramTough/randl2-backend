package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.ssoserver.service.SsdClientService;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 * Created on 2021/1/30 22:09
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"SSO客户端接口"})
@RestController
@RequestMapping("/client")
@ApiResponses(value = {
        @ApiResponse(code = ErrorU.CODE_803.C, message = ErrorU.CODE_803.M),
})
public class SsoClientController extends MyBaseController {

    private final SsdClientService ssdClientService;

    @Autowired
    public SsoClientController(SsdClientService ssdClientService) {
        this.ssdClientService = ssdClientService;
    }

    @ApiOperation(value = "查询一个", notes = "查询一个RandlApp应用")
    @RequestMapping(value = "/one", method = {RequestMethod.GET,RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempClientId", value = "临时客户端ID", paramType = "query",
                    required = true, defaultValue = "23234dsfsdf323dsfs")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<OauthClientDetailsDto> getOne(@Param("tempClientId") String tempClientId) {
        return getSuccessfulApiResponse(ssdClientService.getClientByTempClientId(tempClientId));
    }




}
