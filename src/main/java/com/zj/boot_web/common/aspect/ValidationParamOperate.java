package com.zj.boot_web.common.aspect;

public class ValidationParamOperate extends AspectHandler{
    @Override
    protected ValidationParam factoryMethod() {
        return  new ValidationParam();
    }
}
