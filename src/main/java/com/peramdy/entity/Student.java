package com.peramdy.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by peramdy on 2017/9/16.
 */

@Data
public class Student {

    private Integer id;
    private String stuName;
    private Integer classId;
    private String token;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
