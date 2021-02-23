package com.kangqing.oauthclientsso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里我们创建一个oauth2-client-sso服务作为需要登录的客户端服务，使用上一节中的oauth2-jwt-server服务作为认证服务，
 * 当我们在oauth2-jwt-server服务上登录以后，就可以直接访问oauth2-client-sso需要登录的接口，来演示下单点登录功能。
 */
@SpringBootApplication
public class OauthClientSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthClientSsoApplication.class, args);
    }

}
