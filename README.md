#### spring-boot

````

GET http://${applicationName}/endPointName

actuator 监控与管理endPoint

<!-- actuator(spring-boot 监控端点) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

http://${applicationName}/beans 改为: http://${applicationName}/beansName
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
http://${applicationName}/endPoints/m

````