package com.jeramtough.randl2.resource.service.impl;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.jtweb.component.location.bean.JtLocation;
import com.jeramtough.jtweb.model.error.ErrorS;
import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.resource.service.ResourceLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;


/**
 * <pre>
 * Created on 2021/10/12 上午10:17
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class ResourceLocationServiceImpl implements ResourceLocationService {

    private final LocationGating locationGating;

    @Autowired
    public ResourceLocationServiceImpl(
            LocationGating locationGating) {
        this.locationGating = locationGating;
    }

    @Override
    public JtLocation getLocationByAuto(
            HttpServletRequest httpServletRequest, String lon, String lat) {
        if (!StringUtils.hasText(lat) || !StringUtils.hasText(lon)) {
            return getLocationByIp(httpServletRequest);
        }
        else {
            //根据经纬度获取失败时，转成根据ip获取
            try {
                return getLocationByLonLat(lon, lat);
            }
            catch (Exception e) {
                L.warn(e.getMessage());
                return getLocationByIp(httpServletRequest);
            }
        }
    }

    @Override
    public JtLocation getLocationByLonLat(String lon, String lat) {
        try {
            return locationGating.getLocationByLatLon(lat, lon);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiResponseException(ErrorS.CODE_7.C, "获取地址", e.getMessage());
        }
    }

    @Override
    public JtLocation getLocationByIp(HttpServletRequest httpServletRequest) {
        String ipAddress = IpAddrUtil.getIpAddr(httpServletRequest);

        try {
            return locationGating.getLocationByIpAddress(ipAddress);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiResponseException(ErrorS.CODE_7.C, "获取地址", e.getMessage());
        }
    }
}
