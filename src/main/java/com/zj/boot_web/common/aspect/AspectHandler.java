package com.zj.boot_web.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.zj.boot_web.common.base.PageData;

public abstract class AspectHandler {


    /**
     *  工厂方法模式完成注解功能
     * @param pjp 切面类
     * @param args 方法参数
     * @param method 方法
     * @return
     * @throws Throwable
     */
    public Object doAspectHandler(ProceedingJoinPoint pjp, Object[] args, Method method, PageData data)throws Throwable{
        AspectApi aspectApi  = factoryMethod();
        return aspectApi.doHandlerAspect(args,pjp,method,data);
    }
    protected  abstract AspectApi factoryMethod();

}
