package com.peramdy.dao.slaver;

import com.peramdy.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by peramdy on 2017/9/19.
 */
@Component("studentSlaverDao")
public interface StudentDao {
    Student queryStuInfo(@Param("id") Integer stuId);

    int addStuInfo(Student student);
}
