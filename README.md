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