package com.kangqing.nacosconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可以看到，这个例子中并没有加入nacos的服务发现模块，所以这两个内容是完全可以独立使用的
 *
 * 这个注解 @RefreshScope 主要用来让这个类下的配置内容支持动态刷新，也就是当我们的应用启动之后，修
 * 改了Nacos中的配置内容之后，这里也会马上生效。
 */
@RefreshScope
@RestController
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    /**
     * 注意这个冒号不能省略
     */
    @Value("${kangqing.title:}")
    private String title;

    @Value("${kangqing.mybatis:}")
    private String mybatis;

    @GetMapping("/test")
    public String hello() {
        return "title -> " + title + "\nmybatis -> " + mybatis;
    }

}
