package com.zj.boot_web.common.annotation;

import java.lang.annotation.*;

/**
 * 验证参数是否为空注解
 *TypesName(类名)：ValidationParam
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:36:52
 */
@Target(ElementType.METHOD)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
@Documented
public @interface Param {
    /**
     * 必填参数
     */
    String value() default "";
}
