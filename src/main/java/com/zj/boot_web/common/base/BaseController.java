package com.zj.boot_web.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(BaseController.class);
	private static final long serialVersionUID = 6357869213649815390L;
	
	public static final String URL = Const.URL; // URL地址
	public static final String WECHAT_USER_SESSION_NAME = Const.WECHAT_USER_SESSION_NAME; // session名称

	@Autowired
	private HttpServletRequest request;

	@ModelAttribute
	private void validate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		for (Object key : pd.keySet()) {
			if (pd.get(key) instanceof String) {
				pd.put(key, pd.getString(key).trim());
			}
		}
	}

	// 根据session名称获取session信息
	protected PageData getUserSession() {
		HttpSession session = request.getSession();
		PageData sessionPd = (PageData) session.getAttribute(WECHAT_USER_SESSION_NAME);
		return sessionPd;
	}
	
	// 根据session名称获取session信息
	protected PageData getSession(String sessionName) {
		HttpSession session = request.getSession();
		PageData sessionPd = (PageData) session.getAttribute(sessionName);
		return sessionPd;
	}
	
	// 根据session名称存储session信息
	protected boolean setSession(String sessionName, PageData pd) {
		if (this.removeSession(sessionName)) {
			this.getRequest().getSession().setAttribute(sessionName, pd);//保存session中
			return true;
		}
		return false;
	}
	
	// 存储session信息
	protected boolean setUserSession(PageData pd) {
		if (this.removeSession(WECHAT_USER_SESSION_NAME)) {
			this.getRequest().getSession().setAttribute(WECHAT_USER_SESSION_NAME, pd);//保存session中
			return true;
		}
		return false;
	}

	// 根据session名称清除session信息
	protected boolean removeSession(String sessionName) {
		HttpSession session = request.getSession();
		session.removeAttribute(sessionName);

		if (ComUtil.isEmpty(getSession(sessionName))) {
			return true;
		}

		return false;
	}

	public PageData getPageData() {
		return new PageData(getRequest());
	}

	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		return request;
	}

	public String getBasePath() {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

}
