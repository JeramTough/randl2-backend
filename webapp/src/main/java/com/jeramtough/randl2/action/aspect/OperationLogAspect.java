package com.jeramtough.randl2.action.aspect;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.model.entity.LogOperation;
import com.jeramtough.randl2.service.LogOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020-08-06 15:46
 * by @author JeramTough
 * </pre>
 */
@Aspect
@Component
public class OperationLogAspect {

    private final LogOperationService logOperationService;
    private final HttpServletRequest request;

    @Autowired
    public OperationLogAspect(
            LogOperationService logOperationService,
            HttpServletRequest request) {
        this.logOperationService = logOperationService;
        this.request = request;
    }

    /**
     * 日志切入点
     */
    @Pointcut(
            "@annotation(com.jeramtough.randl2.component.logforoperation.annotation.LoggingOperation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object doAfter(ProceedingJoinPoint joinPoint) throws Exception {

        //获取基本信息
        String simpleMethodName = joinPoint.getSignature().getName();
        boolean isLogining = "login".equals(simpleMethodName);
        boolean isLogout = "logout".equals(simpleMethodName);
        SystemUser beforeSystemUser = null;
        if (isLogout) {
            if (UserHolder.hasLogined()) {
                beforeSystemUser = UserHolder.getSystemUser();
            }
        }

        String methodName = joinPoint.getSignature().getDeclaringTypeName();
        String moduleName = joinPoint.getTarget().getClass().getAnnotation(
                Api.class).tags()[0];

        //获取API方法详细信息
        Class<?>[] argsClass = new Class<?>[joinPoint.getArgs().length];
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            argsClass[i] = joinPoint.getArgs()[i].getClass();
        }
        Method method = null;
        try {
            method = joinPoint.getTarget().getClass().getMethod(simpleMethodName,
                    argsClass);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ApiOperation apiOperationAnnotation = Objects.requireNonNull(method).getAnnotation(
                ApiOperation.class);

        LogOperation logOperation = new LogOperation();
        logOperation.setCreateTime(LocalDateTime.now());
        logOperation.setTags(moduleName);
        logOperation.setApiDescription(apiOperationAnnotation.notes());
        logOperation.setIp(IpAddrUtil.getIpAddr(request));
        logOperation.setApiName(simpleMethodName);

        Map<String, Object> contentMap = new HashMap<>(4);
        contentMap.put("method", methodName);
        contentMap.put("args", joinPoint.getArgs());

        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
            logOperation.setResult(true);
            String returnString = JSON.toJSONString(returnValue);
            returnString = returnString.substring(0, Math.min(returnString.length(), 50));
            returnString = returnString + "...";
            contentMap.put("return", returnString);
        }
        catch (Throwable throwable) {
            if (throwable instanceof Exception) {
                Exception exception = (Exception) throwable;
                logOperation.setResult(false);
                throw exception;
            }
        }
        finally {
            SystemUser systemUser = null;
            if (UserHolder.hasLogined()) {
                systemUser = UserHolder.getSystemUser();
            }
            if (isLogining) {
                //如果是登录操作，最后再影藏账号密码
                contentMap.put("args", null);
            }
            if (isLogout) {
                systemUser = beforeSystemUser;
            }

            if (systemUser != null && systemUser.getUserType() == UserType.ADMIN) {
                logOperation.setAdminId(systemUser.getUid());
                logOperation.setAdminName(systemUser.getUsername());
                String content = JSON.toJSONString(contentMap);
                logOperation.setContent(content);
                logOperationService.add(logOperation);
            }

        }
        return returnValue;
    }
}
