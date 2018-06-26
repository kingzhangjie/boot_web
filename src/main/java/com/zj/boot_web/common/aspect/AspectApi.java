package com.zj.boot_web.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.zj.boot_web.common.base.PageData;

/**
 * Aspect接口
 *TypesName(类名)：AspectApi
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年6月11日下午3:29:08
 */
public interface AspectApi {
    Object doHandlerAspect(Object[] obj, ProceedingJoinPoint pjp, Method method, PageData data)throws Throwable;
}
