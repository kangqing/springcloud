package com.kangqing.kqoauth2authenticationserver.constant;

/**
 * 消息枚举
 * @author kangqing
 * @since 2021/2/24 11:16
 */
public enum MessageConstant {

    LOGIN_SUCCESS("登录成功"),
    USERNAME_PASSWORD_ERROR("用户名或密码错误"),
    CREDENTIALS_EXPIRED("该账户的登录凭证已过期，请重新登录"),
    ACCOUNT_DISABLED("账户已被禁用，请联系管理员"),
    ACCOUNT_LOCKED("该账户已被锁定，请联系管理员"),
    ACCOUNT_EXPIRED("该账户已过期，请联系管理员"),
    PERMISSION_DENIED("没有访问权限，请联系管理员");

    private final String msg;

    MessageConstant(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
