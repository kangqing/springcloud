package com.kangqing.nacosserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启Spring Cloud的服务注册与发现
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
@RestController
public class NacosServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosServerApplication.class, args);
    }


    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name);
        return "hello " + name;
    }

}
