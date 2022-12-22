package com.jeramtough.randl2.common.action.controller;

import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.randl2.common.component.apiinfo.core.ApiInfoRecorder;

/**
 * <pre>
 * Created on 2020/2/6 14:56
 * by @author JeramTough
 * </pre>
 */
public abstract class MyBaseController extends BaseSwaggerController {


    public MyBaseController() {
        ApiInfoRecorder.getInstance().registerApiInfo(this.getClass());
    }

}
