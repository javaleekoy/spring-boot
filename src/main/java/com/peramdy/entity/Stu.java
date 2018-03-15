package com.peramdy.entity;

import com.peramdy.es.spring.ESearchType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author peramdy on 2017/9/16.
 */

public class Stu implements Serializable {

    @ESearchType(type = "Integer")
    private Integer id;
    @ESearchType(analyze = true)
    private String stuName;
    @ESearchType(type = "Integer")
    private Integer classId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
