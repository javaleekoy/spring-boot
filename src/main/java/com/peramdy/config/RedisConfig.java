package com.peramdy.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/12.
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "redis.properties")
public class RedisConfig {

    private String url;

    private int port;

    @DisconfFileItem(name = "redis.url", associateField = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "redis.port", associateField = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInfo() {
        return "url：" + getUrl() + "  port：" + getPort();
    }

}
