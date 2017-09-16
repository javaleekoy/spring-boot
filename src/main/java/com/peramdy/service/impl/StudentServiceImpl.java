package com.peramdy.service.impl;

import com.peramdy.entity.Student;
import com.peramdy.mapper.StudentMapper;
import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by peramdy on 2017/9/16.
 */
@Service
public class StudentServiceImpl implements StudentService {

//    @Autowired
//    private StudentMapper studentMapper;

    @Override
    public Student queryStudentInfoById(Integer id) {
        return null;
//        return studentMapper.queryStudentInfo(id);
    }
}
