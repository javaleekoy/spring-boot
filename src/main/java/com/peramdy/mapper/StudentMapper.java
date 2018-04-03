package com.peramdy.mapper;

import com.peramdy.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by peramdy on 2017/9/16.
 */
@Mapper
public interface StudentMapper {
    Student queryStudentInfo(Integer id);
}
