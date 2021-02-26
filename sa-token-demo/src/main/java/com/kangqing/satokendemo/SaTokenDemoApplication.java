package com.kangqing.satokendemo;

import cn.dev33.satoken.SaTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SaTokenDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenDemoApplication.class, args);
        log.info("启动成功：sa-token配置如下：{}", SaTokenManager.getConfig());
    }



}
