package com.kangqing.kqoauth2api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 搭建一个API服务，它不会集成和实现任何安全相关逻辑，全靠网关来保护它
 */
@SpringBootApplication
public class KqOauth2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KqOauth2ApiApplication.class, args);
    }

}
