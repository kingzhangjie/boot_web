package com.zj.boot_web.common.exception;

/**
 * 身份认证异常
 *TypesName(类名)：UnauthorizedException
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:52:22
 *
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
