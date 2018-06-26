package com.zj.boot_web.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.BaseController;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.base.PublicResult;
import com.zj.boot_web.common.base.PublicResultConstant;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.DateUtil;
import com.zj.boot_web.common.utils.MD5;
import com.zj.boot_web.service.UserService;

@Controller
@RequestMapping("/api/admin/user/")
public class SysUserApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(SysUserApiController.class);
	
	public static final String PREFIX_PATH = "/api/admin/user/"; // 接口前缀路径

	@Autowired
	private UserService userService;
	
	
	@Log(description="接口请求",type="api",userType="admin")
	@PostMapping("login.action")
	@Param(value="account,password")
	@ResponseBody
	public PublicResult<PageData> login(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求登录接口:" + PREFIX_PATH + "login.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		String account = pd.get("account").toString();
		String password = MD5.md5(account + pd.get("password").toString());
		pd.put("password", password);
		PageData user = this.userService.seleteAdminUserByAccountAndPwd(pd);
		if (ComUtil.isEmpty(user)) {
			results = new PublicResult<PageData>(
					PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
		} else {
			String state = user.get("STATE").toString();
			String message = "用户账号异常";
			
			if (ComUtil.equals(state, "01")) {
				this.setUserSession(user);// 保存session中
				log.info("用户使用账号["+ user.get("U_ACCOUNT") +"]登录成功，登录时间：" + DateUtil.getDateTimes());
				results = new PublicResult<PageData>(
						PublicResultConstant.SUCCESS, user); 
				log.info("返回结果==" + results.toString());
				return results;
			} else if (ComUtil.equals(state, "02")) {
				message = "用户账号已被冻结";
			} else if (ComUtil.equals(state, "03")) {
				message = "用户账号已被禁用";
			} else if (ComUtil.equals(state, "04")) {
				message = "用户账号已被注销";
			}
			message += "，如有疑问请联系系统管理员";
			
			PublicResultConstant.INVALID_USER.setMsg(message);
			log.info("用户使用账号["+ user.get("U_ACCOUNT") +"]登录失败，登录时间：" + DateUtil.getDateTimes());
			results = new PublicResult<PageData>(
					PublicResultConstant.INVALID_USER, user);
		}
		
		log.info("返回结果==" + results.toString());
		return results;
	}
}
