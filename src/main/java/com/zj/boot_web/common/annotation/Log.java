package com.zj.boot_web.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

	/**
	* @MethodsName(方法名)：
　　* @Description: 在Controller方法上加入该注解会自动记录日志
　　* @param ${tags}
　　* @return ${return_type}
　　* @throws
　　* @author Adger
　　* @date 2018/6/26 10:37
　　*/

@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Log {
	
	/**
	 * 描述.
	 */
	String description() default "日志记录";
	/*
	 * 类型.
	 */
	String type() default "";
	
	/*
	 * 用户类型
	 */
	String userType() default "";

}
