package com.kangqing.kqoauth2authenticationserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kangqing.kqoauth2authenticationserver.user.dao.RoleMapper;
import com.kangqing.kqoauth2authenticationserver.user.domain.Role;
import com.kangqing.kqoauth2authenticationserver.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yunqing
 * @since 2021-01-01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
