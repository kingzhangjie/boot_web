package com.zj.boot_web.common.base;

/**
 * 
 *TypesName(类名)：PubliccodeConstant
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:09:55
 *
 */
public enum PublicResultConstant {

    /**
     * 异常
     */
    FAILED("90000001", "系统错误"),
    /**
     * 成功
     */
    SUCCESS("00000000", "success"),
    /**
     * 未登录/token过期
     */
    UNAUTHORIZED("90000002", "获取登录用户信息失败"),
    /**
     * 失败
     */
    ERROR("90000000", "操作失败"),
    /**
     * 失败
     */
    PARAM_ERROR("90000003", "参数错误"),
    
    /**
     * 请求类型错误
     */
    REQUEST_METHOD("90000004", "请求类型错误，请尝试POST请求"),
    
    /**
     * 请求路径错误
     */
    REQUEST_ERROR_404("404", "请求错误，请检查访问路径是否正确"),
    
    /**
     * 请求参数错误
     */
    REQUEST_ERROR_500("500", "请求错误，请检查请求参数是否正确"),

    /**
     *
     */
    INVALID_RE_PASSWORD("10000010", "两次输入密码不一致"),
    /**
     * 用户名或密码错误
     */
    INVALID_PASSWORD("10000009", "旧密码错误"),
    /**
     * 用户不存在
     */
    INVALID_USER("10000001", "用户不存在"),
    /**
     * 用户名重复
     */
    USERNAME_ALREADY_IN("10000002", "用户名已存在"),
    
    /**
     * 账号重复
     */
    ACCOUNT_ALREADY_IN("10000002", "账号已存在"),
    
    /**
     * 手机号重复
     */
    PHONE_ALREADY_IN("10000002", "手机号已存在"),
    
    /**
     * 账号或手机号重复
     */
    ACCOUNT_PHONE_ALREADY_IN("10000002", "账号或手机号已存在"),

    /**
     * 账号或密码错误
     */
    INVALID_USERNAME_PASSWORD("10000003", "账号或密码错误"),
    
    
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("10000003", "验证码错误"),
    
    /**
     * 验证码发送失败
     */
    SEND_FAILURE("10000003", "抱歉，验证码发送失败"),
    
    /**
     * 验证码失效
     */
    CAPTCHA_FAILURE("10000003", "验证码超时"),
    
    /**
     * 授权失效
     */
    ACCREDIT_FAILURE("10000003", "微信授权访问身份失效，请退出重新授权访问"),
    
    /**
     * 角色不存在
     */
    INVALID_ROLE("10000004", "角色不存在"),


    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST("10000005", "请求参数已存在"),
    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY("10000006", "请求参数为空"),
    /**
     * 没有权限
     */
    USER_NO_PERMITION("10000007", "当前用户无该接口权限"),

    /**
     * 角色不存在
     */
    ROLE_USER_USED("10000008", "角色使用中，不可删除"),
    
    /**
     * 用戶登录身份失效
     */
    USER_LOGIN_FAILURE("10000009", "用戶登录身份失效，请重新登录"),
    
    /**
     * 网络安全环境异常
     */
    NETWORK_SECURITY_ERROR("10000010", "用戶登录身份失效，请重新登录"),
    
    /**
     * 页面超时
     */
    OVERTIME_ERROR("10000011", "操作超时，刷新页面后重试"),
    
    /**
     * 网络环境异常
     */
    NETWORK_ERROR("10000012", "当前请求网络环境异常，请稍后再试")
    
    
    ;


    public String code;
    public String msg;

    PublicResultConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
