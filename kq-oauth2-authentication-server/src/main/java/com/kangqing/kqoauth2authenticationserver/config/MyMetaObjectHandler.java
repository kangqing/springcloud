package com.kangqing.kqoauth2authenticationserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus之自动填充字段功能
 * 个人比较喜欢这个功能，尤其是用于create_time和update_time两个字段的自动填充。
 * @author yunqing
 * @since 2020/5/13 22:29
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 实现添加的时候自动填充的字段
     * 添加记录的时候自动设置创建时间和修改时间为当前时间
     * @param metaObject
     */

    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("delCode", 0, metaObject);
        this.setFieldValByName("version", 1L, metaObject);
    }

    /**
     * 修改的时候自动填充的字段, 填充为当前时间
     * @param metaObject
     */

    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
