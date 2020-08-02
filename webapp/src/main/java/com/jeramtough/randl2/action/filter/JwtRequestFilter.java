package com.jeramtough.randl2.action.filter;

import com.jeramtough.jtweb.action.filter.BaseSwaggerFilter;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.service.RegisteredUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Created on 2020/3/22 23:52
 * by @author JeramTough
 * </pre>
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter implements BaseSwaggerFilter {

    private final RegisteredUserLoginService registeredUserLoginService;

    @Autowired
    public JwtRequestFilter(
            RegisteredUserLoginService registeredUserLoginService) {
        this.registeredUserLoginService = registeredUserLoginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
            IOException {
        if (request.getServletPath() != null && request.getServletPath().contains(
                "registeredUser/logined")) {
            String token = parseToken(request);
            try {
                registeredUserLoginService.loginByExistingToken(token);
                filterChain.doFilter(request, response);
            }
            catch (ApiResponseException e) {
                CommonApiResponse apiResponse = getFailedApiResponse(e);
                returnRestfulApiResponse(apiResponse, response);
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }


    //********************

    private String parseToken(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
// JWT Token is in the form "Bearer token". Remove Bearer word and get
// only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        return jwtToken;
    }
}
