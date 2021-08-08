package com.jeramtough.randl2.common.action.filter;

import com.jeramtough.jtweb.action.filter.BaseSwaggerFilter;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Created on 2020/11/21 23:52
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseCredentialsTokenFilter extends AbstractAuthenticationProcessingFilter implements
        BaseSwaggerFilter {

    protected BaseCredentialsTokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected BaseCredentialsTokenFilter(
            RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException,
            ServletException {


        //当授权通过，缓存授权令牌到Context
//        super.successfulAuthentication(request, response, chain, authResult);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        if (getRememberMeServices() != null && getRememberMeServices().getClass() != NullRememberMeServices.class) {
            getRememberMeServices().loginSuccess(request, response, authResult);
        }

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }

        //放行过滤器
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException,
            ServletException {
        ApiResponseException apiResponseException = new ApiResponseException(ErrorU.CODE_6.C, failed.getMessage());
        returnCommonApiResponse(getFailedApiResponse(apiResponseException), response);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException,
                    ServletException {
                if (exception instanceof BadCredentialsException) {
                    exception = new BadCredentialsException(exception.getMessage(),
                            new BadClientCredentialsException());
                }
                returnCommonApiResponse(getFailedApiResponse(exception), response);
            }
        });
        setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException,
                    ServletException {
                // no-op - just allow filter chain to continue to token endpoint
            }
        });
    }

}
