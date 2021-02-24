package com.kangqing.kqoauth2api.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.kangqing.kqoauth2api.domain.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kangqing
 * @since 2021/2/24 14:33
 */
@Component
public class LoginUserHandler {

    public UserDTO getCurrentUser(){
        //从Header中获取用户信息,当gateway中的AuthGlobalFilter鉴权通过后会将信息存进header后续不用解析token
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userJsonObject.getStr("user_name"));
        userDTO.setId(Convert.toLong(userJsonObject.get("id")));
        userDTO.setRoles(Convert.toList(String.class,userJsonObject.get("authorities")));
        return userDTO;
    }
}