package com.kangqing.satokendemo.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.kangqing.satokendemo.dto.UserDTO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 * @author kangqing
 * @since 2021/2/26 15:01
 */
@Component //保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    List<UserDTO> list;

    /**
     * 模拟数据库中的用户信息
     */
    @PostConstruct
    private void init() {
        list = List.of(new UserDTO(1L, "admin", "123456", "1", List.of("ADMIN", "TEST")),
                new UserDTO(2L, "test", "123456", "1", List.of("TEST")));
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        return List.of("user-add", "user-delete", "user-update", "user-get", "article-get");
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        System.out.println(loginKey);
        System.out.println(loginId);
        final List<List<String>> collect = list.stream().filter(e -> e.getId() == Long.parseLong(loginId.toString()))
                .map(UserDTO::getRoles)
                .collect(Collectors.toList());
        return collect.get(0);
    }

}
