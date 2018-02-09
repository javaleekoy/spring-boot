package com.peramdy.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author peramdy on 2017/9/16.
 */

@Data
public class Stu {

    private Integer id;
    private String stuName;
    private Integer classId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
