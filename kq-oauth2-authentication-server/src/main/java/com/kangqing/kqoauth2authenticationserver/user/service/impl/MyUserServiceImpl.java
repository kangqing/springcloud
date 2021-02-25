package com.kangqing.kqoauth2authenticationserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kangqing.kqoauth2authenticationserver.user.dao.UserMapper;
import com.kangqing.kqoauth2authenticationserver.user.domain.User;
import com.kangqing.kqoauth2authenticationserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yunqing
 * @since 2020-05-12
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyUserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
