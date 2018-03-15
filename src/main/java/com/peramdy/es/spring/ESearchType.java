package com.peramdy.es.spring;

import java.lang.annotation.*;

/**
 * Created by peramdy on 2018/3/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESearchType {

    /**
     * 字段类型
     *
     * @return
     */
    String type() default "string";

    /**
     * 是否分词
     *
     * @return
     */
    boolean analyze() default false;

}
