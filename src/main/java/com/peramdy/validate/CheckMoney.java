package com.peramdy.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author peramdy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MoneyValidation.class)
public @interface CheckMoney {

    String message() default "金额不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
