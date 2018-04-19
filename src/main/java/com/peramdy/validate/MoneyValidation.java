package com.peramdy.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author peramdy
 */
public class MoneyValidation implements ConstraintValidator<CheckMoney, Double> {

    /**
     * 表示金额的正则表达式
     **/
    private String moneyReg = "^\\d+(\\.\\d{1,2})?$";
    private Pattern moneyPattern = Pattern.compile(moneyReg);

    @Override
    public void initialize(CheckMoney constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null) {
            //金额是空的，返回true，是因为如果null，则会有@NotNull进行提示
            //如果这里false的话，那金额是null，@CheckMoney中的message也会进行提示
            return false;
        }

        boolean reVal = moneyPattern.matcher(value.toString()).matches();
        return reVal;
    }
}
