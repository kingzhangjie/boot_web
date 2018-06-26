package com.zj.boot_web.controller;

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
import com.zj.boot_web.service.StatisticalService;

@Controller
@RequestMapping("/api/stat/")
public class StatisticalApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(StatisticalApiController.class);
	
	public static final String PREFIX_PATH = "/api/stat/"; // 接口前缀路径

	@Autowired
	private StatisticalService statisticalService;
	
	
	@Log(description="接口请求",type="api")
	@PostMapping("test.action")
	@Param(value="user")
	@ResponseBody
	public PublicResult<PageData> test(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求测试接口:" + PREFIX_PATH + "test.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		
		log.info("返回结果==" + results.toString());
		return results;
	}
}
