package com.peramdy.controller.student;

import com.peramdy.entity.Student;
import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by peramdy on 2017/9/16.
 */
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String queryStuInfo(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return null;
        else
            return student.toString();
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String StuInfo(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return null;
        else
            return student.toString();
    }

    @ResponseBody
    @RequestMapping("/add")
    public String addStuInfo(@RequestBody Student stu) {
        Student student = studentService.addStuInfo(stu);
        if (student == null)
            return null;
        else
            return student.toString();
    }

}
