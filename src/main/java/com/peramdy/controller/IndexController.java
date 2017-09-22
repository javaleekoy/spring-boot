package com.peramdy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by peramdy on 2017/9/19.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "forward:/login.jsp";
    }

}
