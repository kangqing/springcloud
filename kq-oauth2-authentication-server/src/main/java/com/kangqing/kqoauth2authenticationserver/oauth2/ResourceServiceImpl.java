package com.kangqing.kqoauth2authenticationserver.oauth2;

import com.kangqing.kqoauth2authenticationserver.cache.RedisCacheTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 创建一个资源服务ResourceServiceImpl，初始化的时候把资源与角色匹配关系缓存到Redis中，方便网关服务进行鉴权的时候获取。
 * @author kangqing
 * @since 2021/2/24 11:41
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl {

    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    private final RedisCacheTemplate redisTemplate;

    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/api/hello", List.of("ADMIN"));
        resourceRolesMap.put("/api/user/currentUser", List.of("ADMIN", "TEST"));
        redisTemplate.hSet(RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
