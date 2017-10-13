package com.peramdy.annotation;

import java.lang.annotation.*;

/**
 * Created by peramdy on 2017/10/13.
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserId {
}
