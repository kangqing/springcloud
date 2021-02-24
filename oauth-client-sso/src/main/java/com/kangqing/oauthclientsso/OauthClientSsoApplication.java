package com.kangqing.oauthclientsso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这里我们创建一个oauth2-client-sso服务作为需要登录的客户端服务，使用上一节中的oauth2-jwt-server服务作为认证服务，
 * 当我们在oauth2-jwt-server服务上登录以后，就可以直接访问oauth2-client-sso需要登录的接口，来演示下单点登录功能。
 * 启动单点登录功能 @EnableOAuth2Sso
 */
@RestController
@RequestMapping("/user")
@EnableOAuth2Sso
@SpringBootApplication
public class OauthClientSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthClientSsoApplication.class, args);
    }

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }

    /**
     * 需要 admin 权限才能访问
     * @return
     */
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/admin")
    public Object adminAuth() {
        return "Has admin auth!";
    }

}
