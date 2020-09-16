package com.jeramtough.randl2.adminapp.action.aspect;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtweb.util.IpAddrUtil;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.component.userdetail.UserType;
import com.jeramtough.randl2.common.model.entity.OperationLog;
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

    private final OperationLogService operationLogService;
    private final HttpServletRequest request;

    @Autowired
    public OperationLogAspect(
            OperationLogService operationLogService,
            HttpServletRequest request) {
        this.operationLogService = operationLogService;
        this.request = request;
    }

    /**
     * 日志切入点
     */
    @Pointcut(
            "@annotation(com.jeramtough.randl2.adminapp.component.logforoperation.annotation.LoggingOperation)")
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

        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(LocalDateTime.now());
        operationLog.setTags(moduleName);
        operationLog.setApiDescription(apiOperationAnnotation.notes());
        operationLog.setIp(IpAddrUtil.getIpAddr(request));
        operationLog.setApiName(simpleMethodName);

        Map<String, Object> contentMap = new HashMap<>(4);
        contentMap.put("method", methodName);
        contentMap.put("args", joinPoint.getArgs());

        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
            operationLog.setResult(true);
            String returnString = JSON.toJSONString(returnValue);
            returnString = returnString.substring(0, Math.min(returnString.length(), 50));
            returnString = returnString + "...";
            contentMap.put("return", returnString);
        }
        catch (Throwable throwable) {
            if (throwable instanceof Exception) {
                Exception exception = (Exception) throwable;
                operationLog.setResult(false);
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
                operationLog.setAdminId(systemUser.getUid());
                operationLog.setAdminName(systemUser.getUsername());
                String content = JSON.toJSONString(contentMap);
                operationLog.setContent(content);
                operationLogService.add(operationLog);
            }

        }
        return returnValue;
    }
}
