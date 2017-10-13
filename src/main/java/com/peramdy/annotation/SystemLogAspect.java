package com.peramdy.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.minidev.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by peramdy on 2017/9/29.
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Pointcut("execution(* com.peramdy.controller..*.*(..))")
    public void controllerAspect() {
    }

    @Pointcut("@annotation(com.peramdy.annotation.ServiceLogger)")
    public void serviceAnnotationAspect() {
    }

    /**
     * controller 抓取数据
     *
     * @param joinPoint
     */
    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        logger.info(">>>>>>>>>>>>Aspect:ControllerInfo<<<<<<<<<<<<<<<<<<");
        logger.info("IP:" + ip);
        logger.info("className:" + joinPoint.getTarget().getClass().getName());
        logger.info("methodName:" + joinPoint.getSignature().getName() + "()");
        String params = "";

        Object[] args = joinPoint.getArgs();
        if (args != null || args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest)
                    continue;
                if (args[i] instanceof HttpServletResponse)
                    continue;
                params += JSON.toJSONString(args[i]);
                System.out.println(args[i]);
            }
            if (StringUtils.isNotBlank(params))
                logger.info("methodParameters:" + params);
            else
                logger.info("methodParameters:empty!");
        } else {
            logger.info("methodParameters:empty!");
        }

    }


    /**
     * service 层 抓取异常数据
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "serviceAnnotationAspect()", throwing = "ex")
    public void doAfter(JoinPoint joinPoint, Throwable ex) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        logger.info(">>>>>>>>>>>>Aspect:ServiceInfo<<<<<<<<<<<<<<<<<<");
        logger.info("IP:" + ip);
        logger.info("className:" + joinPoint.getTarget().getClass().getName());
        logger.info("methodName:" + joinPoint.getSignature().getName() + "()");
        try {
            logger.info("methodDesc:" + getServiceMethodDescription(joinPoint));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                Object arg = joinPoint.getArgs()[i];
                if (arg instanceof HttpServletRequest)
                    continue;
                else if (arg instanceof HttpServletResponse)
                    continue;
                else
                    params += JSON.toJSONString(arg);
            }
        }
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() +
                joinPoint.getSignature().getName(), ex.getClass().getName(), ex.getMessage(), params);

    }


    public static String getServiceMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    description = method.getAnnotation(ServiceLogger.class).desc();
                    break;
                }
            }
        }
        return description;
    }
    
}
