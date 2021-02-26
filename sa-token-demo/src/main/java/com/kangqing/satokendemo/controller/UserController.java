package com.kangqing.satokendemo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.kangqing.satokendemo.dto.UserDTO;
import com.kangqing.satokendemo.result.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 * @author kangqing
 * @since 2021/2/26 13:50
 */
@RestController
@RequestMapping("/user")
public class UserController {



    /*@PostMapping("/login")
    public JsonResult<?> login(String username, String password) {
        // 从数据库查询出登录的账户
        final List<UserDTO> collect = list.stream()
                .filter(e -> e.getUsername().equals(username) && e.getPassword().equals(password))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            // 匹配到则进行登录
            StpUtil.setLoginId(collect.get(0).getId());
            return JsonResult.success();
        }
        return JsonResult.fail();
    }*/


}
