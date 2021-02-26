package com.kangqing.satokendemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author kangqing
 * @since 2021/2/26 13:52
 */
@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String status;

    private List<String> roles;
}
