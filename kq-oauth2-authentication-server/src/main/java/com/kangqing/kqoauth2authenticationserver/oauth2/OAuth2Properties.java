package com.kangqing.kqoauth2authenticationserver.oauth2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kangqing
 * @since 2021/2/24 21:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "kq.oauth2")
public class OAuth2Properties {
    /**
     * 给哪些应用授权，可以多个，默认为空
     */
    private OAuth2ClientProperties[] clients = {};
    /**
     * token 存在 redis 还是存在 jwt，不配置默认存在 jwt
     */
    private String storeType;
}
