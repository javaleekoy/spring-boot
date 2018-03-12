package com.peramdy.config.disconf;

import com.baidu.disconf.client.common.annotations.DisconfItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/12.
 */
@Service
@Scope("singleton")
public class AppConfig {

    private String appName;

    private String desc;

    @DisconfItem(key = "appName")
    public String getAppName() {
        return appName;
    }


    public void setAppName(String appName) {
        this.appName = appName;
    }

    @DisconfItem(key = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInfo() {
        return "name：" + getAppName() + "  desc：" + getDesc();
    }

}
