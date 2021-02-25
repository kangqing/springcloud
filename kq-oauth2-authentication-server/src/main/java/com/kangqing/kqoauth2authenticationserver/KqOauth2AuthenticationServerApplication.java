package com.kangqing.kqoauth2authenticationserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.kangqing.kqoauth2authenticationserver.user.dao"})
public class KqOauth2AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KqOauth2AuthenticationServerApplication.class, args);
    }

}
