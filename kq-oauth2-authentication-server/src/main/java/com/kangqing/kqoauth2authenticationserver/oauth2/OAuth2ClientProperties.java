package com.kangqing.kqoauth2authenticationserver.oauth2;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 客户端配置
 * @author kangqing
 * @since 2021/2/24 21:22
 */
@Getter
@Setter
public class OAuth2ClientProperties {
    /**
     * 应用的clientId
     */
    private String clientId;
    /**
     * 应用的clientSecret
     */
    private String clientSecret;
    /**
     * 令牌的有效时间 默认设置2小时，因为系统默认的token有效期是0秒不配置就是0
     */
    private int accessTokenValiditySeconds = 7200;
    /**
     * 这个应用支持的授权模式有哪些"authorization_code","refresh_token","password","implicit"
     */
    private String authorizedGrantTypes;
    /**
     * 授权的权限等级
     */
    private String scopes;
}
