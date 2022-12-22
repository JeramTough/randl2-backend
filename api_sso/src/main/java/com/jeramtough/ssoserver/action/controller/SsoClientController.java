package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.ssoserver.service.SsdClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2021/1/30 22:09
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "SSO客户端接口")
@RestController
@RequestMapping("/client")
@ApiResponses(value = {
        @ApiResponse(responseCode = ErrorU.CODE_803.C, description = ErrorU.CODE_803.M),
})
public class SsoClientController extends MyBaseController {

    private final SsdClientService ssdClientService;

    @Autowired
    public SsoClientController(SsdClientService ssdClientService) {
        this.ssdClientService = ssdClientService;
    }

    @Operation(summary = "查询一个", description = "查询一个RandlApp应用")
    @RequestMapping(value = "/one", method = {RequestMethod.GET,RequestMethod.POST})
    @Parameters({
            @Parameter(name = "tempClientId", value = "临时客户端ID", 
                    required = true, example = "23234dsfsdf323dsfs")})
    
    public CommonApiResponse<OauthClientDetailsDto> getOne(@Param("tempClientId") String tempClientId) {
        return getSuccessfulApiResponse(ssdClientService.getClientByTempClientId(tempClientId));
    }




}
