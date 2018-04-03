package com.peramdy.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author pd
 */
@Aspect
@Component
public class PdAspectDataSource {

    private static Logger logger = LoggerFactory.getLogger(PdAspectDataSource.class);

    @Before(value = "@annotation(PdDS)")
    public void changeDs(JoinPoint joinPoint) {
        /**数据源默认值**/
        String value = PdDsEnum.DEFAULT.getValue();
        /**获取目标类**/
        Class<?> cla = joinPoint.getTarget().getClass();
        /**获取目标方法**/
        String methodName = joinPoint.getSignature().getName();
        /**获取目标方法入参数据类型**/
        Class[] args = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        try {
            /**目标方法**/
            Method method = cla.getMethod(methodName, args);
            if (method.isAnnotationPresent(PdDS.class)) {
                PdDS pdDS1 = method.getAnnotation(PdDS.class);
                value = pdDS1.value().getValue();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        logger.info("sourceType：[{}]", value);
        PdDynamicDsContextHolder.setDataSourceKey(value);
    }

    @After(value = "@annotation(PdDS)")
    public void removeDs(JoinPoint joinPoint) {
        logger.info("removeDs：[{}]", PdDynamicDsContextHolder.getDataSourceKey());
        PdDynamicDsContextHolder.clean();
    }


}
