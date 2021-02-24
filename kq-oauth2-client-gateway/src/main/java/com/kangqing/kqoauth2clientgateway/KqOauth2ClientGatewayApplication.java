package com.kangqing.kqoauth2clientgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务，它将作为OAuth2的资源服务、客户端服务使用，对访问微服务的请求进行统一的校验认证和鉴权操作。
 */
@SpringBootApplication
public class KqOauth2ClientGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KqOauth2ClientGatewayApplication.class, args);
    }

}
