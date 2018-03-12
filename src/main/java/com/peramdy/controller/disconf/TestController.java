package com.peramdy.controller.disconf;

import com.peramdy.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pd 2018/3/12.
 */
@RestController
@RequestMapping("/disconf")
public class TestController {

    @Autowired
    private RedisConfig disconfService;

    @GetMapping("/info")
    public Object getInfo() {
        return disconfService.getInfo();
    }

}
