package com.kangqing.kqoauth2authenticationserver.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 负责令牌的存取
 * @author kangqing
 * @since 2021/2/25 17:27
 */
@Configuration
@RequiredArgsConstructor
public class TokenStoreConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * 只有在找到配置项且值为redis的时候才生效,目前仅支持配置成 jwt
     * @return
     */
    /*@Bean
    @ConditionalOnProperty(prefix = "kq.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }*/

    /**
     * 目前仅支持配置成 jwt
     * 配置Jwt的相关配置
     * 注解 @ConditionalOnProperty 检查系统配置kq.oauth2.storeType当值是jwt时候下面生效，
     * matchIfMissing 如果没匹配到这个配置项，下面的代码也生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "kq.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        /**
         * 只管token存取，不管生成
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore(){

            return new JwtTokenStore(accessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setKeyPair(keyPair());
            return jwtAccessTokenConverter;
        }


        @Bean
        public static KeyPair keyPair() {
            //从classpath下的证书中获取秘钥对
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"),
                    "123456".toCharArray());
            return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
        }

    }

}
