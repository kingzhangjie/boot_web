package com.zj.boot_web.common.exception;

/**
 * 参数异常
 *TypesName(类名)：ParamJsonException
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:51:54
 *
 */
public class ParamJsonException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ParamJsonException() {}

    public ParamJsonException(String message) {
        super(message);
        this.message = message;
    }


}
