package com.zj.boot_web.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.exception.ParamJsonException;
import com.zj.boot_web.common.utils.ComUtil;

/**
 * 验证参数
 *TypesName(类名)：ValidationParam
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年6月13日上午9:54:20
 *
 */
public class ValidationParam implements AspectApi {
	
    @Override
    public Object doHandlerAspect(Object [] obj, ProceedingJoinPoint pjp, Method method, PageData data) throws Throwable{
    	//获取注解的value值返回
    	Param param = method.getAnnotation(Param.class);
        String value = param.value();
        if(!ComUtil.isEmpty(value)){
        	if(!ComUtil.isEmpty(data)){
        		//验证字段非空
                String[] columns = value.split(",");
                String missCol = "";
                for (String column : columns) {
                    if (ComUtil.isEmpty(data.get(column))) {
                        missCol += column + "  ";
                    }
                }
                if (!ComUtil.isEmpty(missCol)) {
                    throw new ParamJsonException("缺少必须参数：" + missCol.trim()); 
                }
        	} else {
        		throw new ParamJsonException("当前请求缺少必需参数：" + value);
        	}
        }
        return obj;
    }

}
