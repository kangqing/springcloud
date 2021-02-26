package com.kangqing.kqoauth2authenticationserver.oauth2;

import com.kangqing.kqoauth2authenticationserver.jwt.JwtTokenEnhancer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 * @author kangqing
 * @since 2021/2/24 11:33
 */
@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenEnhancer jwtTokenEnhancer;
    private final OAuth2Properties oAuth2Properties;
    private final TokenStore redisTokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    /*
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400);
    }*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(oAuth2Properties.getClients())){

            for (OAuth2ClientProperties config : oAuth2Properties.getClients()) {
                /**
                 * 按照逗号分隔放进数组
                 */
                String[] authorizedGrantTypes = StringUtils.splitByWholeSeparator(config.getAuthorizedGrantTypes(), ",");
                String[] scopes = StringUtils.splitByWholeSeparator(config.getScopes(), ",");
                builder.withClient(config.getClientId())
                        .secret(passwordEncoder.encode(config.getClientSecret()))
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds()) //令牌的有效时间 我设置了两小时7200秒
                        .authorizedGrantTypes(authorizedGrantTypes) //针对这个应用支持的授权模式有哪些？
                        //接受一个数组，我写的支持刷新，授权码和密码模式 implicit简化模式没配置，所以不支持
                        .refreshTokenValiditySeconds(604800) //刷新令牌的有效时间，设置一个星期，也可自定义配置
                        .scopes(scopes); //也是一个数组，授权的权限等级
            }

        }

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        /**
         * 默认情况下自己适配
         * 但是我们这里继承AuthorizationServerConfigurerAdapter 自定义之后，需要手动配置
         */
        /* 没配置 redis token 方案
        endpoints
                .tokenStore(redisTokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);*/

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> delegates = new ArrayList<>();
            delegates.add(jwtTokenEnhancer);
            delegates.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器
            endpoints.authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService) //配置加载用户信息的服务
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .tokenEnhancer(enhancerChain);
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }


}
