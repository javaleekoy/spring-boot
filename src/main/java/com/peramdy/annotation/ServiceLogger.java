package com.peramdy.annotation;

import java.lang.annotation.*;

/**
 * Created by peramdy on 2017/9/29.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLogger {
    String desc() default "";
}
