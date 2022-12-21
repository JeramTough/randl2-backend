package com.jeramtough.randl2.resource.service;

import com.jeramtough.jtweb.component.location.bean.JtLocation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <pre>
 * Created on 2021/10/12 上午10:14
 * by @author WeiBoWen
 * </pre>
 */
public interface ResourceLocationService {

    JtLocation getLocationByAuto(HttpServletRequest httpServletRequest,String lon, String lat);

    JtLocation getLocationByLonLat(String lon, String lat);

    JtLocation getLocationByIp(HttpServletRequest httpServletRequest);
}
