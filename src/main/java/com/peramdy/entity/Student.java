package com.peramdy.entity;

import com.peramdy.validate.CheckMoney;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by peramdy on 2017/9/16.
 */

@Data
public class Student {

    private Integer id;
    private String stuName;
    private Integer classId;
    @CheckMoney(message = "金额必填，且格式要xx.xx")
    private Double money;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(String[] args) {

        Student student = new Student();
        student.setId(1);
        student.setStuName("ha");
        student.setClassId(1);
        student.setMoney(11.111);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(student);
        for (ConstraintViolation<Object> c : set) {
            System.out.println(c.getPropertyPath() + " : " + c.getMessage());
        }
    }

}
