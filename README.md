## spring-boot

##### actuator

````

请求： GET http://${applicationName}/endPointName

actuator 监控与管理endPoint

<!-- actuator(spring-boot 监控端点) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

请求： http://${applicationName}/beans 改为: http://${applicationName}/beansName
endpoints.beans.id=beansome
其他
endpoints.xxxx.id=oooo

关闭端点 metrics 
endpoints.metrics.enabled=false

关闭所有端点，仅开启metrics端点
endpoints.enabled=false
endpoints.metrics.enabled=true

修改 metrics 端点请求路径
endpoints.metrics.path=/endPoints/m
请求： http://${applicationName}/endPoints/m

````
##### actuator 查看端点信息
````

请求： http://${applicationName}/actuator

<!-- hateoasUI (查看监控端点url) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

请求： http://${applicationName}/actuator

<!-- hateoas(查看监控端点url：图形化界面) -->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>hal-browser</artifactId>
</dependency>

请求： http://${applicationName}/docs

<!-- actuator文档插件 有详细的actuator介绍-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-actuator-docs</artifactId>
</dependency>

````
##### PS: 监控还可以依赖 [spring-boot-admin](https://github.com/codecentric/spring-boot-admin)

##### spring-boot 整合docker

```

<!-- maven打包动态修改替换占位符 @@-->
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
    </resource>
</resources>

<!-- docker-maven-plugin -->
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.4.13</version>
    <configuration>

        <!-- 配置dockerHost 必须开启docker remote api -->
        <dockerHost>http://192.168.136.130:2375</dockerHost>

        <!-- 配置docker私服 -->
        <!--<serverId>dockerNexus</serverId>-->
        <!--<registryUrl>http://192.168.136.130:18082</registryUrl>-->

        <!-- docker镜像名称 -->
        <imageName>${project.artifactId}</imageName>

        <!-- 可选项 每次docker:build都重新tag -->
        <forceTags>true</forceTags>

        <!-- docker tag 可多个tag -->
        <imageTags>
            <imageTag>latest</imageTag>
            <imageTag>${project.version}</imageTag>
        </imageTags>

        <!-- Dockerfile 所在的文件目录 -->
        <dockerDirectory>${project.build.outputDirectory}</dockerDirectory>

        <resources>
            <resource>
                <!-- 用于指定需要复制的根目录 ${project.build.directory} 表示 target 目录 -->
                <directory>${project.build.directory}</directory>
                <!-- 指定要复制的文件，即为maven打包后生产的jar文件 -->
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>

docker 远程api设置   -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
docker 本地仓库设置  -- insecure-registry 192.168.136.130:18082 (http)

```
##### spring-boot 添加 shell 脚本一件打包
```
#!/bin/bash
#
# 打包
#
cd "${0%/*}"
cd cd ..
echo " starting to clean zk-api "
mvn clean -DskipTests -U
echo " cleaning zk-api finished "
echo " starting  to install zk-api "
mvn install -U
echo " install zk-api finished "
echo " docker building  "
mvn docker:build
echo " docker build finished "
exit
```
##### disconf 配置中心
```
1.依赖jar包
<!-- disconf-client -->
<dependency>
    <groupId>com.baidu.disconf</groupId>
    <artifactId>disconf-client</artifactId>
    <version>2.6.36</version>
</dependency>

2.添加disconf的支持
@Configuration
public class DisconfConfig {
    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean getDisconfMgrBean() {
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        //扫描包
        disconfMgrBean.setScanPackage("com.peramdy");
        return disconfMgrBean;
    }
    @Bean(destroyMethod = "destroy", initMethod = "init")
    public DisconfMgrBeanSecond getDisconfMgrBeanSecond() {
        DisconfMgrBeanSecond disconfMgrBeanSecond = new DisconfMgrBeanSecond();
        return disconfMgrBeanSecond;
    }
}

3.配置disconf.properties
# 是否使用远程配置文件
# true(默认)会从远程获取配置 false则直接获取本地配置
enable.remote.conf=true
#
# 配置服务器的 HOST,用逗号分隔  127.0.0.1:8000,127.0.0.1:8000
#
conf_server_host=192.168.136.130:18015
# 版本, 请采用 X_X_X_X 格式
version=1_0_0_0
# APP 请采用 产品线_服务名 格式
app=spring-boot
# 环境
env=local
# debug
debug=true
# 忽略哪些分布式配置，用逗号分隔
ignore=
# 获取远程配置 重试次数，默认是3次
conf_server_url_retry_times=1
# 获取远程配置 重试时休眠时间，默认是5秒
conf_server_url_retry_sleep_seconds=1

4.上传redis.properties文件到disconf-web

5.加载配置文件
@Service
@Scope("singleton")
@DisconfFile(filename = "redis.properties")
public class RedisConf {
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

ps:disconf修改配置文件值后项目不需重启

```
##### elasticSearch
```
<!-- elasticsearch -->
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>5.6.8</version>
</dependency>
<!-- transport -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>5.6.8</version>
</dependency>

分词器
elasticsearch-analysis-ik （5.6.8）
elasticsearch-analysis-pinyin（5.6.8）

自定义分析器

需要理解elasticsearch的setttings和mapping的概念
Setting 是 针对于索引库而言 可以设置索引库的分片数量 和 副本数量
Mappings相当于数据库中对字段的类型约束 以及 某些字段查询时指定分词器 

添加分词器
PUT peramdy
{
  "mappings": {
    "huahua":{
      "properties": {
        "stuName":{
          "type":"text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        },
        "id":{
          "type":"integer"
        },
        "classId":{
          "type":"integer"
        }
      }
    }
  }
}

```