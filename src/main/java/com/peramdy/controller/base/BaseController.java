package com.peramdy.controller.base;

import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by peramdy on 2017/9/28.
 */
public class BaseController {

    @Autowired
    private StudentService studentService;

    @ModelAttribute("stuId")
    public Integer getUserId(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return studentService.queryUserIdByToken(token);
    }

}
