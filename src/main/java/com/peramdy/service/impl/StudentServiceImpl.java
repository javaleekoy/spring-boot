package com.peramdy.service.impl;

import com.peramdy.dao.master.StudentDao;
import com.peramdy.entity.Student;
import com.peramdy.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by peramdy on 2017/9/16.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public Student queryStudentInfoById(Integer id) {
        return studentDao.queryStudentInfo(id);
    }

    @Override
    public Student addStuInfo(Student student) {
        int primaryId = studentDao.addStuInfo(student);
        student.setId(primaryId);
        return student;
    }
}
