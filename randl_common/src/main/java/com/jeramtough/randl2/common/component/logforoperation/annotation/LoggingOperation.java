package com.jeramtough.randl2.common.component.logforoperation.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 必须要获得操作员对象才真正进行记录，也就是说，这个注释必须
 * 放在操作员登录以后进行的接口，除了登录接口以外
 *
 * Created on 2020-08-06 15:38
 * by @author JeramTough
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggingOperation {


}
