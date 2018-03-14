package com.peramdy.controller.disconf;

import com.peramdy.config.disconf.AppConfig;
import com.peramdy.config.disconf.RedisConfig;
import com.peramdy.es.EsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pd 2018/3/12.
 */
@RestController("disconfTestController")
@RequestMapping("/disconf")
public class TestController {

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private EsConfig esConfig;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/redis")
    public Object getRedisInfo() {
        return redisConfig.getInfo();
    }


    @GetMapping("/app")
    public Object getAppInfo() {
        return appConfig.getInfo();
    }

    @GetMapping("/es")
    public Object getEsInfo() {
        return esConfig.getInfo();
    }
}
