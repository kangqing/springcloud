package com.kangqing.oauth2jwtserver.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * oauth2-server 是把令牌存储在内存中，这样如果部署多个服务则不可用，
 * 这节有两种解决办法，一是存储在 redis 中
 * 二是，使用 jwt 存储
 * 使用 redis 存储 token 的配置
 * @author kangqing
 * @since 2021/2/23 20:49
 */
@Configuration
@RequiredArgsConstructor
public class RedisTokenStoreConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    /* 使用了 jwtTokenStore
    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }*/
}
