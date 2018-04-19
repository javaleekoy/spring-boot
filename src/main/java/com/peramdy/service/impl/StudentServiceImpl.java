package com.peramdy.service.impl;

import com.peramdy.config.datasource.PdDS;
import com.peramdy.config.datasource.PdDsEnum;
import com.peramdy.dao.dynamic.StudentDao;
import com.peramdy.entity.Student;
import com.peramdy.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pd
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    @PdDS(value = PdDsEnum.DS_SLAVE)
    public Student queryStudentInfoById_slave(Integer id) {
        return studentDao.queryStuInfo(id);
    }

    @Override
    @PdDS(value = PdDsEnum.DS_SLAVE)
    public int addStuInfo_slave(Student student) {
        return studentDao.addStuInfo(student);
    }

    @Override
    @PdDS(value = PdDsEnum.DS_MASTER)
    public Student queryStudentInfoById_master(Integer id) {
        return studentDao.queryStuInfo(id);
    }

    @Override
    @PdDS(value = PdDsEnum.DS_MASTER)
    public int addStuInfo_master(Student student) {
        return studentDao.addStuInfo(student);
    }

    @Override
    public Student queryStudentInfoById_default(Integer id) {
        return studentDao.queryStuInfo(id);
    }

    @Override
    public int addStuInfo_default(Student student) {
        return studentDao.addStuInfo(student);
    }
}
