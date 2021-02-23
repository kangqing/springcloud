package com.kangqing.apigateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第二种方式配置网关，第一种是配置文件配置
 * @author kangqing
 * @since 2021/2/23 9:56
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    @Value("${service-url.nacos-config:}")
    private String nacosConfig;

    private final RouteLocatorBuilder builder;

    @Bean
    public RouteLocator customRouteLocator() {
        return builder.routes()
                // 路由id是 path_route2，相当于把请求道localhost:7013/config/getUsername的请求转移到了
                // localhost:7007/config/getUsername上
                .route("path_route2", r -> r.path("/config/getUsername")
                .uri(nacosConfig + "/config/getUsername"))
                .build();
    }
}
