package com.kangqing.kqoauth2authenticationserver.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangqing.kqoauth2authenticationserver.user.domain.User;
import com.kangqing.kqoauth2authenticationserver.user.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yunqing
 * @since 2020-05-12
 */
public interface UserMapper extends BaseMapper<User> {

}
