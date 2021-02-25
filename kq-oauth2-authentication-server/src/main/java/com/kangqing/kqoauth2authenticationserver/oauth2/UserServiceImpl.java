package com.kangqing.kqoauth2authenticationserver.oauth2;

import cn.hutool.core.collection.CollUtil;
import com.kangqing.kqoauth2authenticationserver.constant.MessageConstant;
import com.kangqing.kqoauth2authenticationserver.user.dto.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理业务实现类
 * @author kangqing
 * @since 2021/2/24 11:06
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;

    private final PasswordEncoder passwordEncoder;

    private final HttpServletRequest request;


    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new UserDTO(1L,"kangqing", password,1, CollUtil.toList("ADMIN")));
        userList.add(new UserDTO(2L,"kk", password,1, CollUtil.toList("TEST")));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(s))
                .collect(Collectors.toList());
        /**
         * 根据应用 clientId 获取不同的用户信息
        String clientId = request.getParameter("client_id");
        UserDTO userDto;
        if(AuthConstant.ADMIN_CLIENT_ID.equals(clientId)){
            userDto = adminService.loadUserByUsername(username);
        }else{
            userDto = memberService.loadUserByUsername(username);
        }*/
        /*if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR.getMsg());
        }*/
        //userDto.setClientId(clientId);
        //SecurityUser securityUser = new SecurityUser(userDto);
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED.getMsg());
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED.getMsg());
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED.getMsg());
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED.getMsg());
        }
        return securityUser;
    }
}
