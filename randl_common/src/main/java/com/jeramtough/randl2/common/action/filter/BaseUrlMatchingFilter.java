package com.jeramtough.randl2.common.action.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Created on 2021/8/9 上午10:44
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseUrlMatchingFilter extends GenericFilterBean {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private final String defaultFilterProcessesUrl;
    private AntPathRequestMatcher requiresAuthenticationRequestMatcher;

    public BaseUrlMatchingFilter(String defaultFilterProcessesUrl) {
        this.defaultFilterProcessesUrl = defaultFilterProcessesUrl;
        this.setFilterProcessesUrl(defaultFilterProcessesUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!this.requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
        }
        else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Request is to processing");
            }

            doFilterContinue(servletRequest, servletResponse, filterChain);
        }

    }

    public abstract void doFilterContinue(ServletRequest servletRequest,
                                 ServletResponse servletResponse,
                                 FilterChain filterChain) throws ServletException, IOException;

    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return this.requiresAuthenticationRequestMatcher.matches(request);
    }

    //*************

    private void setFilterProcessesUrl(String defaultFilterProcessesUrl) {
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(
                defaultFilterProcessesUrl);
    }
}
