package com.peramdy.es;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/13.
 */
@Service
@DisconfFile(filename = "es.properties")
@Scope("singleton")
public class EsConfig {

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.port}")
    private int port;

    @DisconfFileItem(name = "es.url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "es.port")
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
