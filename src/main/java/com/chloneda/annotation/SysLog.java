package com.chloneda.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author chloneda
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";

}
