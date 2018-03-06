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