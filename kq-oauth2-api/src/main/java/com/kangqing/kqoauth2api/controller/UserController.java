package com.kangqing.kqoauth2api.controller;

import com.kangqing.kqoauth2api.domain.UserDTO;
import com.kangqing.kqoauth2api.handler.LoginUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author kangqing
 * @since 2021/2/24 14:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController{

    private final LoginUserHandler loginUserHandler;

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        return loginUserHandler.getCurrentUser();
    }

}
