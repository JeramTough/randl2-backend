/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeramtough.randl2.clent1.controller;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author JeramTough
 */
@Api(tags = {"OAuth客户端接口"})
@RestController
public class ClientController extends BaseSwaggerController {

    private String messagesBaseApi = "http://127.0.0.1:9086/randl2/resource/user/test";
    private String messagesBaseApi1 = "http://127.0.0.1:9086/randl2/resource/open/test";

    @Autowired
    @Qualifier("oAuth2RestTemplate")
    private OAuth2RestTemplate oAuth2RestTemplate;


    @GetMapping(value = "/goAuthorize")
    public CommonApiResponse<String> goAuthorize() {
        L.arrive();
        return getSuccessfulApiResponse(
                "http://127.0.0.1:9085/randl2/authserver/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=authorization-code-client&state=1234");
    }

    @GetMapping("/authorized")        // registered redirect_uri for authorization_code
    public CommonApiResponse<String> authorized(HttpServletRequest request) {
        L.arrive();
        return getSuccessfulApiResponse("得到授权回调到这里Code:"+request.getParameter("code"));
    }


}