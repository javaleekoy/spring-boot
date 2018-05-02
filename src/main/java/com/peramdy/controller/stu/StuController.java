package com.peramdy.controller.stu;

import com.peramdy.entity.Stu;
import com.peramdy.exception.PdNullPointerException;
import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author peramdy on 2017/9/16.
 */
@RestController
@RequestMapping("/stu")
public class StuController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/info")
    public String stuInfo1(@RequestParam("stuId") Integer stuId) {
        Stu student = studentService.queryStudentInfoById(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }


    /**
     * RequestParam , RequestMapping 使用
     *
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String stuInfo2(@RequestParam("stuId") Integer stuId) {
        Stu student = studentService.queryStudentInfoById(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    /**
     * GetMapping, PathVaribale 使用
     *
     * @param stuId
     * @return
     */
    @GetMapping(value = "/info/{stuId}")
    public String stuInfo3(@PathVariable("stuId") Integer stuId) {
        Stu student = studentService.queryStudentInfoById(stuId);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public String addStuInfo(@RequestBody Stu stu) {
        Stu student = studentService.addStuInfo(stu);
        if (student == null) {
            return null;
        } else {
            return student.toString();
        }
    }

    @GetMapping("/throwEx")
    public String throwException() throws Exception {
        throw new Exception();
    }

    @GetMapping("/throwPdEx")
    public String throwPdException() throws Exception {
        throw new PdNullPointerException();
    }

}
