package com.peramdy.mgo;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.mongodb.MongoClient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author pd 2018/3/19.
 */
@Service
@DisconfFile(filename = "mg.properties")
public class MgConf {

    private String url;
    private int port;
    private String userName;
    private String password;

    @DisconfFileItem(name = "mg.url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "mg.port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @DisconfFileItem(name = "mg.userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DisconfFileItem(name = "mg.password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    @Bean
    public MongoClient createMongoClient() {
        MongoClient mongo = new MongoClient("192.168.136.130", 27017);
        return mongo;
    }


}
