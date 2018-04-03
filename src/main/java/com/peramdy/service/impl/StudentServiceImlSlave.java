package com.peramdy.service.impl;

import com.peramdy.dao.dynamic.StudentDao;
import com.peramdy.entity.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pd
 */
@Service
public class StudentServiceImlSlave {

    @Resource
    private StudentDao studentDao;

    public Student queryStudentInfoById_slave(Integer id) {
        return studentDao.queryStuInfo(id);
    }

    public int addStuInfo_slave(Student student) {
        return studentDao.addStuInfo(student);
    }

}
