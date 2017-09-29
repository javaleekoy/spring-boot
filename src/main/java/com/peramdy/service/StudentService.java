package com.peramdy.service;

import com.peramdy.entity.Student;

/**
 * Created by peramdy on 2017/9/16.
 */
public interface StudentService {

    /**
     * @param id
     * @return
     */
    Student queryStudentInfoById(Integer id);

    /**
     * @param student
     * @return
     */
    Student addStuInfo(Student student);

    /**
     * @param id
     * @return
     */
    Student queryStudentInfoById_slaver(Integer id);

    /**
     * @param student
     * @return
     */
    int addStuInfo_slaver(Student student);

    /**
     * @param token
     * @return
     */
    int queryUserIdByToken(String token);

}
