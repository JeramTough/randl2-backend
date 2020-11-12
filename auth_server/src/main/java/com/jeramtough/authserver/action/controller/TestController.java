package com.jeramtough.authserver.action.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/3/18 16:41
 * by @author JeramTough
 * </pre>
 */
@RestController
public class TestController {


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }


}
