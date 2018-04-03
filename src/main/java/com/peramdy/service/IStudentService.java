package com.peramdy.service;

import com.peramdy.entity.Student;

/**
 * Created by peramdy on 2017/9/16.
 */
public interface IStudentService {

    /**
     * query
     *
     * @param id
     * @return
     */
    Student queryStudentInfoById_slave(Integer id);

    /**
     * add
     *
     * @param student
     * @return
     */
    int addStuInfo_slave(Student student);


    /**
     * query
     *
     * @param id
     * @return
     */
    Student queryStudentInfoById_master(Integer id);

    /**
     * add
     *
     * @param student
     * @return
     */
    int addStuInfo_master(Student student);


    /**
     * query
     *
     * @param id
     * @return
     */
    Student queryStudentInfoById_default(Integer id);

    /**
     * add
     *
     * @param student
     * @return
     */
    int addStuInfo_default(Student student);

}
