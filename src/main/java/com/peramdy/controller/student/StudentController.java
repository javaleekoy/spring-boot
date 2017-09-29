package com.peramdy.controller.student;

import com.peramdy.common.Enum.ResponseCodeEnum;
import com.peramdy.controller.base.BaseController;
import com.peramdy.entity.Student;
import com.peramdy.service.StudentService;
import com.peramdy.utils.ResponseSimpleUtil;
import com.peramdy.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by peramdy on 2017/9/16.
 */
@RestController
@RequestMapping("/stu")
public class StudentController extends BaseController {

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
    public ResponseSimpleUtil StuInfo(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return new ResponseSimpleUtil(ResponseCodeEnum.OK);
        else
            return new ResponseUtil<Student>(ResponseCodeEnum.OK, student);
    }


    @RequestMapping(value = "/info2", method = RequestMethod.GET)
    public ResponseSimpleUtil StuInfoToken(@ModelAttribute("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return new ResponseSimpleUtil(ResponseCodeEnum.OK);
        else
            return new ResponseUtil<Student>(ResponseCodeEnum.OK, student);
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseSimpleUtil addStuInfo(@RequestBody Student stu) {
        Student student = studentService.addStuInfo(stu);
        if (student == null)
            return new ResponseUtil<Object>(ResponseCodeEnum.FAIL);
        else
            return new ResponseUtil<Object>(ResponseCodeEnum.OK);
    }


    @RequestMapping(value = "/slaver/info", method = RequestMethod.GET)
    public String StuInfo_slaver(@RequestParam("stuId") Integer stuId, HttpEntity<String> httpEntity, HttpServletRequest request) {

        HttpHeaders headers = httpEntity.getHeaders();

        String ip = request.getRemoteAddr();

        System.out.println(ip);

        Student student = studentService.queryStudentInfoById_slaver(stuId);
        if (student == null)
            return null;
        else
            return student.toString();
    }

    @ResponseBody
    @RequestMapping("/slaver/add")
    public String addStuInfo_slaver(@RequestBody Student stu) {
        int student = studentService.addStuInfo_slaver(stu);
        if (student > 0)
            return "success";
        else
            return "fail";
    }

}
