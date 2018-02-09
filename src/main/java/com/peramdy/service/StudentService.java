package com.peramdy.service;

import com.peramdy.entity.Stu;
import com.peramdy.mapper.StuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author peramdy on 2017/9/16.
 */
@Service
public class StudentService {

    @Resource
    private StuMapper stuMapper;

    public Stu queryStudentInfoById(Integer id) {
        return stuMapper.queryStuInfo(id);
    }

    public Stu addStuInfo(Stu student) {
        int primaryId = stuMapper.addStuInfo(student);
        student.setId(primaryId);
        return student;
    }


}
