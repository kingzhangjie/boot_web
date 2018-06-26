package com.zj.boot_web.common.aspect;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.config.SpringContextBean;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.service.StatisticalService;

public class RecordLog implements AspectApi {

	private Logger logger = LoggerFactory.getLogger(RecordLog.class);

	public static final String WECHAT_USER_SESSION_NAME = Const.WECHAT_USER_SESSION_NAME; // 微信用户session名称
	public static final String ADMIN_USER_SESSION_NAME = Const.ADMIN_USER_SESSION_NAME; // 系统管理员用户session名称

	@Autowired
	private HttpServletRequest request;

	@Override
	public Object doHandlerAspect(Object[] obj, ProceedingJoinPoint pjp,
			Method method, PageData data) throws Throwable {
		Log log = method.getAnnotation(Log.class);
		// 异常日志信息
		String actionLog = null;
		StackTraceElement[] stackTrace = null;
		// 是否执行异常
		boolean isException = false;
		// 接收时间戳
		long endTime;
		// 开始时间戳
		long operationTime = System.currentTimeMillis();
		try {
			return pjp.proceed(obj);
		} catch (Throwable throwable) {
			isException = true;
			actionLog = throwable.getMessage();
			stackTrace = throwable.getStackTrace();
			throw throwable;
		} finally {
			// 日志处理
			logHandle(pjp, method, log, actionLog, operationTime, isException,
					stackTrace);
		}
	}

	private void logHandle(ProceedingJoinPoint joinPoint, Method method,
			Log log, String actionLog, long startTime, boolean isException,
			StackTraceElement[] stackTrace) {
		StatisticalService statisticalService = SpringContextBean
				.getBean(StatisticalService.class);
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		request = sra.getRequest();
		// String authorization = request.getHeader("Authorization");
		PageData pd = new PageData();
		PageData user = new PageData();
		if (ComUtil.equals(log.userType(), "admin")) {
			user = getSession(ADMIN_USER_SESSION_NAME);
		} else {
			user = getSession(WECHAT_USER_SESSION_NAME);
		}
		if (!ComUtil.isEmpty(user)) {
			pd.put("L_USER_ID", user.get("ID"));
			pd.put("L_USER_NAME", user.get("NAME"));
		}
		user = new PageData(request);
		pd.put("L_URL", request.getRequestURL().toString());
		pd.put("L_URI", request.getRequestURI());
		pd.put("L_CLASS_NAME", joinPoint.getTarget().getClass().getName());
		pd.put("L_METHOD_NAME", method.getName());
		if (!ComUtil.isEmpty(user)) {
			pd.put("L_PARAMETER", JSONObject.toJSONString(user));
		}
		pd.put("L_METHOD", request.getMethod());
		pd.put("L_TYPE", log.type());
		pd.put("L_PORT",
				request.getRemotePort() + "," + request.getServerPort() + ","
						+ request.getLocalPort());
		pd.put("L_OS", getOs());
		pd.put("L_BROWSER", getBrowser());
		pd.put("L_IP", getIpAddr());
		pd.put("L_DESCRIPTION", log.description());
		pd.put("L_IS_SUCCEED", String.valueOf(isException));
		pd.put("L_MESSAGE", actionLog);
		pd.put("L_USER_AGENT", request.getHeader("User-Agent"));
		if (isException) {
			StringBuilder sb = new StringBuilder();
			sb.append(actionLog + " &#10; ");
			for (int i = 0; i < stackTrace.length; i++) {
				sb.append(stackTrace[i] + " &#10; ");
			}
			pd.put("L_STACKTRACE", sb.toString());
		}
		
		logger.info("执行方法信息:" + JSONObject.toJSON(pd));
		Object o = statisticalService.insertLogRecord(pd);
		logger.info("添加日志文件信息结果:" + o);
	}

	// 根据session名称获取session信息
	protected PageData getSession(String sessionName) {
		HttpSession session = request.getSession();
		PageData sessionPd = (PageData) session.getAttribute(sessionName);
		return sessionPd;
	}

	public String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
												// = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/*
	 * 获取操作系统
	 */
	public String getOs() {
		String os = "";
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		// =================OS Info=======================
		if (userAgent.indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.indexOf("iphone") >= 0) {
			os = "IPhone";
		} else if (userAgent.indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.indexOf("x11") >= 0) {
			os = "Unix";
		} else {
			os = "UnKnown";
		}
		return os;
	}

	/*
	 * 获取浏览器
	 */
	public String getBrowser() {
		String browser = "";
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if (userAgent.contains("chrome")) {
			browser = "Chrome";
		} else if (userAgent.contains("msie")) {
			browser = "IE";
		} else if (userAgent.contains("firefox")) {
			browser = "Firefox";
		} else if (userAgent.contains("micromessenger")) {
			browser = "WeChat";
		} else {
			browser = "UnKnown";
		}
		return browser;
	}
}
