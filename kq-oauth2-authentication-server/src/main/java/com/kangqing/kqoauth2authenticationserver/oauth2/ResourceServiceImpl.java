package com.kangqing.kqoauth2authenticationserver.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kangqing.kqoauth2authenticationserver.cache.RedisCacheTemplate;
import com.kangqing.kqoauth2authenticationserver.user.domain.MenuResource;
import com.kangqing.kqoauth2authenticationserver.user.domain.Role;
import com.kangqing.kqoauth2authenticationserver.user.domain.RoleResource;
import com.kangqing.kqoauth2authenticationserver.user.service.MenuResourceService;
import com.kangqing.kqoauth2authenticationserver.user.service.RoleResourceService;
import com.kangqing.kqoauth2authenticationserver.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 创建一个资源服务ResourceServiceImpl，初始化的时候把资源与角色匹配关系缓存到Redis中，方便网关服务进行鉴权的时候获取。
 * @author kangqing
 * @since 2021/2/24 11:41
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl {

    @Value("${spring.application.name}")
    private String applicationName;

    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    private final RedisCacheTemplate redisTemplate;

    private final MenuResourceService menuResourceService;

    private final RoleService roleService;

    private final RoleResourceService roleResourceService;

    /* 自定义的角色、权限对应键值对存储到redis中
    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/api/hello", List.of("ADMIN"));
        resourceRolesMap.put("/api/user/currentUser", List.of("ADMIN", "TEST"));
        redisTemplate.hSet(RESOURCE_ROLES_MAP, resourceRolesMap);
    }*/

    /**
     * 项目启动时把 资源-角色对应关系写到 redis 中
     * @return
     */
    @PostConstruct
    public Map<String,List<String>> initResourceRolesMap() {
        Map<String,List<String>> resourceRoleMap = new TreeMap<>();
        List<MenuResource> resourceList = menuResourceService.list(new QueryWrapper<>());
        List<Role> roleList = roleService.list(new QueryWrapper<>());
        List<RoleResource> relationList = roleResourceService.list(new QueryWrapper<>());
        for (MenuResource resource : resourceList) {
            Set<Long> roleIds = relationList.stream()
                    .filter(item -> item.getMenuId().equals(resource.getId()))
                    .map(RoleResource::getRoleId)
                    .collect(Collectors.toSet());
            List<String> roleNames = roleList.stream()
                    .filter(item -> roleIds.contains(item.getId()))
                    .map(item -> item.getId() + "_" + item.getName())
                    .collect(Collectors.toList());
            resourceRoleMap.put("/" + applicationName + resource.getUrl(), roleNames);
        }
        redisTemplate.delete(RESOURCE_ROLES_MAP);
        redisTemplate.hSet(RESOURCE_ROLES_MAP, resourceRoleMap);
        return resourceRoleMap;

    }
}
