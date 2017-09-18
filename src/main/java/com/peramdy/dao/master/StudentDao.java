package com.peramdy.dao.master;

import com.peramdy.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * Created by peramdy on 2017/9/18.
 */
public interface StudentDao {
    Student queryStudentInfo(@Param("id") Integer id);
    int addStuInfo(Student student);
}
