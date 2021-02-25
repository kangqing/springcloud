package com.kangqing.kqoauth2authenticationserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kangqing.kqoauth2authenticationserver.user.dao.MenuResourceMapper;
import com.kangqing.kqoauth2authenticationserver.user.domain.MenuResource;
import com.kangqing.kqoauth2authenticationserver.user.service.MenuResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author yunqing
 * @since 2021-01-01
 */
@Service
public class MenuResourceServiceImpl extends ServiceImpl<MenuResourceMapper, MenuResource> implements MenuResourceService {

}
