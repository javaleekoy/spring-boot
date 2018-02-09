package com.peramdy.mapper;

import com.peramdy.entity.Stu;
import org.apache.ibatis.annotations.Param;

/**
 * @author peramdy on 2017/9/16.
 */
public interface StuMapper {

    /**
     * query stu info
     *
     * @param id
     * @return
     */
    Stu queryStuInfo(@Param("id") Integer id);


    /**
     * save stu
     *
     * @param student
     * @return
     */
    int addStuInfo(Stu student);
}
