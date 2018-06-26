package com.zj.boot_web.common.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.utils.Const;

@Component
@Aspect
public class AspectConfig {

	/**
	 * Log注解
	 */
	@Pointcut("@annotation(com.zj.boot_web.common.annotation.Log)")
	private void recordLog() {
	}
	
	/**
     * 验证参数
     */
    @Pointcut("@annotation(com.zj.boot_web.common.annotation.Param)")
    public void validationParam(){
    }

	/**
	 * 记录网站访问记录
	 */
	/*@Pointcut("execution(* com.subao.controller..*(..))  ")
	public void visitRecord() {
	}*/

	@SuppressWarnings("unused")
	/**
	 * 定制一个环绕通知
	 * 
	 * @param joinPoint
	 */
	@Around("recordLog() || validationParam()")
	public Object validationPoint(ProceedingJoinPoint point) throws Throwable {
		
		Object[] args = point.getArgs();
		Method method = currentMethod(point, point.getSignature().getName());
		boolean isLogEmpty = Objects.isNull(method.getAnnotation(Log.class));
		boolean isValidationParamEmpty = Objects.isNull(method.getAnnotation(Param.class));
		//boolean isCheckEmpty = Objects.isNull(method.getAnnotation(Check.class));
		//boolean isValidationParamEmpty = ComUtil.isEmpty(StringUtil.getMethodAnnotationOne(method,ValidationParam.class.getSimpleName()));
		if (!isValidationParamEmpty) {
			PageData data = new PageData(getRequest());
			AspectHandler aspectHandler = new ValidationParamOperate();
			aspectHandler.doAspectHandler(point, args, method, data);
		}
		if (!isLogEmpty && Const.IS_LOG) {
			AspectHandler aspectHandler = new RecordLogOperate();
			return aspectHandler.doAspectHandler(point, args, method, null);
		}
		
		return point.proceed(args);
	}

	/**
	 * 获取目标类的所有方法，找到当前要执行的方法
	 */
	private Method currentMethod(ProceedingJoinPoint joinPoint,
			String methodName) {
		Method[] methods = joinPoint.getTarget().getClass().getMethods();
		Method resultMethod = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				resultMethod = method;
				break;
			}
		}
		return resultMethod;
	}

	/*@Before("visitRecord()")
	public void before() {
		System.out.println("已经记录下操作日志@Before 方法执行前");
	}

	@After("visitRecord()")
	public void after() {
		System.out.println("已经记录下操作日志@After 方法执行后");
	}*/
	
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		return request;
	}

}
