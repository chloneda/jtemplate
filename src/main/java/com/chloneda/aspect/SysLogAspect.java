package com.chloneda.aspect;

import cn.hutool.json.JSONUtil;
import com.chloneda.annotation.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 系统日志，切面处理类
 *
 * @author chloneda
 */
@Aspect
@Component
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Pointcut("@annotation(com.chloneda.annotation.SysLog)")
    public void sysLog() {

    }

    @Before("sysLog()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SysLog logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(SysLog.class);
        if (logAnnotation != null) {
            String apiName = logAnnotation.value();
            logger.info("============= Start: {} ============================", apiName);
        }else {
            logger.info("========================== Start ==========================");
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.nonNull(attributes)){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> enumeration = request.getHeaderNames();

            StringBuffer headers = new StringBuffer();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String value = request.getHeader(name);
                headers.append(name + ":" + value).append(",");
            }

            // 记录下请求内容
            logger.info("URL          : {}", request.getRequestURL().toString());
            logger.info("HTTP_METHOD  : {}", request.getMethod());
            logger.info("IP           : {}", request.getRemoteAddr());
            logger.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("HEADERS      : {}", headers);
            logger.info("ARGS         : {}", JSONUtil.toJsonStr(joinPoint.getArgs()));
        }
    }

    @Around("sysLog()")
    public Object doAfterReturning(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("Response Args  : {}", JSONUtil.toJsonStr(result).length() < 50 ? JSONUtil.toJsonStr(result) : JSONUtil.toJsonStr(result).substring(0, 50));
        // 执行耗时
        stopWatch.stop();
        logger.info("Time-Consuming : {} ms", stopWatch.getTotalTimeMillis());
        logger.info("========================== End ==========================");
        return result;
    }

}
