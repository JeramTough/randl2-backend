package com.jeramtough.randl2.action.filter;

import com.jeramtough.jtlog.facade.L;
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
public class JwtRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
            IOException {
        if (request.getServletPath()!=null&&request.getServletPath().contains(
                "registeredUserLogined")){
            L.arrive();
        }
        filterChain.doFilter(request, response);
    }
}
