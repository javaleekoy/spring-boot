package com.peramdy.dao.dynamic;

import com.peramdy.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author pd
 */
public interface StudentDao {
    /**
     * query
     *
     * @param stuId
     * @return
     */
    Student queryStuInfo(@Param("id") Integer stuId);

    /**
     * add
     *
     * @param student
     * @return
     */
    int addStuInfo(Student student);
}
