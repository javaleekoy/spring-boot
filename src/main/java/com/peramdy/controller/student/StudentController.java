package com.peramdy.controller.student;

import com.peramdy.entity.Student;
import com.peramdy.service.IStudentService;
import com.peramdy.service.impl.StudentServiceImlSlave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pd
 */
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentServiceImlSlave studentServiceImlSlave;

    @ResponseBody
    @GetMapping(value = "/master/info")
    public String stuInfo_master(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById_master(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/slave/info", method = RequestMethod.GET)
    public String stuInfo_slave(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById_slave(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/default/info", method = RequestMethod.GET)
    public String stuInfo_default(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById_default(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/slave2/info", method = RequestMethod.GET)
    public String stuInfo_slave2(@RequestParam("stuId") Integer stuId) {
        Student student = studentServiceImlSlave.queryStudentInfoById_slave(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/master/add", method = RequestMethod.GET)
    public String addStuInfo_master(@RequestBody Student stu) {
        int student = studentService.addStuInfo_master(stu);
        if (student > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping("/slave/add")
    public String addStuInfo_slaver(@RequestBody Student stu) {
        int student = studentService.addStuInfo_slave(stu);
        if (student > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping("/default/add")
    public String addStuInfo_default(@RequestBody Student stu) {
        int student = studentService.addStuInfo_default(stu);
        if (student > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/slave2/add", method = RequestMethod.GET)
    public String addStuInfo_slave2(@RequestBody Student stu) {
        int student = studentServiceImlSlave.addStuInfo_slave(stu);
        if (student > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

}
