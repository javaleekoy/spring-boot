package com.peramdy.service.impl;

import com.peramdy.dao.master.StudentDao;
import com.peramdy.entity.Student;
import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by peramdy on 2017/9/16.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentMasterDao;

    @Autowired
    private com.peramdy.dao.slaver.StudentDao studentSlaverDao;

    @Override
    public Student queryStudentInfoById(Integer id) {
        return studentMasterDao.queryStudentInfo(id);
    }

    @Override
    public Student addStuInfo(Student student) {
        int primaryId = studentMasterDao.addStuInfo(student);
        student.setId(primaryId);
        return student;
    }


    @Override
    public Student queryStudentInfoById_slaver(Integer id) {
        return studentSlaverDao.queryStuInfo(id);
    }

    @Override
    public int addStuInfo_slaver(Student student) {
        return studentSlaverDao.addStuInfo(student);
    }
}
