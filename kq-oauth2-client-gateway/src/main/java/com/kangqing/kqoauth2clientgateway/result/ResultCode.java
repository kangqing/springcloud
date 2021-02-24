package com.kangqing.kqoauth2clientgateway.result;

/**
 * @author kangqing
 * @since 2020/5/9 13:55
 * 返回码定义
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 */
public enum ResultCode {

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID("1001", "参数无效"),
    PARAM_IS_BLANK("1002", "参数为空"),
    PARAM_TYPE_ERROR("1003", "参数类型错误"),
    PARAM_NOT_COMPLETE("1004", "参数缺失"),
    PARAM_ERROR("1005", "参数错误"),

    /* 用户错误 */
    USER_NOT_LOGIN("2001", "用户未登录"),
    USER_ACCOUNT_EXPIRED("2002", "账号已过期"),
    USER_CREDENTIALS_ERROR("2003", "密码错误"),
    USER_CREDENTIALS_EXPIRED("2004", "密码过期"),
    USER_ACCOUNT_DISABLE("2005", "账号不可用"),
    USER_ACCOUNT_LOCKED("2006", "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST("2007", "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST("2008", "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS("2009", "账号下线"),

    /* 业务错误 */
    NO_PERMISSION("3001", "没有权限"),
    SESSION_TIMEOUT("3002", "session超时"),

    /**
     * token相关
     */
    TOKEN_EXPIRED("5001", "token 已过期，请重新登录！"),
    TOKEN_PARSE_ERROR("5002", "token 解析失败，请尝试重新登录！"),
    TOKEN_OUT_OF_CTRL("5003", "当前用户已在别处登录，请尝试更改密码或重新登录！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD("405", "请求方式不支持！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND("404", "请求不存在！"),

    /**
     * HTTP通信使用
     */
    SUCCESS("200", "SUCCESS"),
    COMMON_FAIL("201", "默认错误"),
    ERROR("500", "服务器内部错误"),
    NOT_FOUND("404", "接口不存在"),
    FAIL("400", "接口异常,请联系管理员"),
    UNAUTHORIZED("401", "未认证（签名错误）"),


    /**
     * security
     */
    LOGIN_ERROR("-990", "登录失败"),
    OVER_MAX_LOGIN_NUM("-991", "当前在线人数过多，请稍后登录"),
    REFRESH_TOKEN_EXPIRED("-997", "用户刷新令牌refresh_token过期,请重新登录"),
    LIMITED_AUTHORITY("-1000", "权限不够"),
    UN_LOGIN("-999", "用户未登录或账号已在其它地方登陆，请重新登录"),
    TOKEN_ILLEGAL("-996", "用户令牌不合法"),
    IP_NOT_ALLOW("-980", "登录的IP不被允许"),

    /**
     * oauth2
     */
    MISSING_CLIENT_INFO("-1009", "客户端注册信息缺失"),
    CLIENT_ID_ALREADY_EXIST("-1010", "client_id已存在"),

    /**
     * 导入导出
     */
    DATA_IS_NULL("4901", "请选择数据导出"),
    IMPORT_FAIL("4902", "未知错误，导入失败"),


//===========  系统管理
    /**
     * 用户管理
     */
    USER_ADD_FAIL("7001", "新增用户失败"),
    USER_UPDATE_FAIL("7002", "修改用户失败"),
    USER_DELETE_FAIL("7003", "删除用户失败"),
    USER_FINDNOWUSER_FAIL("7004", "获取登录用户失败"),
    USER_FINDUSER_FAIL("7005", "获取用户失败"),
    USER_FINDLIST_FAIL("7006", "获取用户列表失败"),
    USER_OUT_FAIL("7007", "导出用户列表失败"),
    USER_IMPORT_FAIL("7008", "导入用户列表失败"),



    /**
     * 角色管理
     */
    ROLE_ADD_FAIL("7101", "新增角色失败"),
    ROLE_UPDATE_FAIL("7102", "修改角色失败"),
    ROLE_DELETE_FAIL("7103", "删除角色失败"),
    ROLE_LIST_FAIL("7104", "获取角色列表失败"),
    ROLE_FINDROLE_FAIL("7105", "获取角色失败"),
    ROLE_USERLIST_FAIL("7106", "获取角色对应用户列表失败"),
    ROLE_USERROLES_FAIL("7107", "获取用户能操作角色集合失败");

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     * @param code
     * @return
     */
    public static String getMessageByCode(String code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
