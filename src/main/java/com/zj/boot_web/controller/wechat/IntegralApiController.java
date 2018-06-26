/**
 * 
 */
package com.zj.boot_web.controller.wechat;

import java.util.List;

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
import com.zj.boot_web.service.IntegralService;

/**
 * TypesName(类名)：IntegralController
 * Description(描述)：积分中心
 * @author Adger
 * @date 2018年6月25日 下午3:34:23
 */
@Controller
@RequestMapping("/api/wx/integral/")
public class IntegralApiController extends BaseController{

private Logger log = LoggerFactory.getLogger(IntegralApiController.class);
	
	public static final String PREFIX_PATH = "/api/wx/integral/"; // 接口前缀路径
	
	@Autowired
	private IntegralService integralService;
	
	/**
	 *MethodsName(方法名)：integralCenter
	 *Description(描述)：积分中心
	 * @param  @param request
	 * @param  @return
	 * @param  @throws Exception
	 * @return PublicResult<PageData>
	 * @author Adger
	 * @date 2018年6月25日 上午11:51:41
	 */
	@Log(description="接口请求",type="api")
	@PostMapping("integralCenter.action")
	@Param(value="U_ID")
	@ResponseBody
	public PublicResult<PageData> integralCenter(HttpServletRequest request) throws Exception {
		log.info("=====================================请求查询积分中心接口:" + PREFIX_PATH + "integralCenter.action=====================================");
		PageData pd = new PageData();
		PageData result = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(PublicResultConstant.ERROR, null);
		//获取用户id
		pd.put("U_ID", pd.get("U_ID"));
		pd.put("STATE", "00");
		//根据用户id获取用户积分
		PageData integralPd = this.integralService.selectWechatIntegralByUId(pd);
		//根据用户id获取用户积分详细列表
		List<PageData> IntegralDetailList = this.integralService.selectWechatIntegralDetailList(pd);
		
		if (ComUtil.isEmpty(IntegralDetailList)) {
			result.put("IntegralDetailList", null);
		}else{
			result.put("IntegralDetailList", IntegralDetailList);
		}
		
		//用户没有积分
		if (ComUtil.isEmpty(integralPd)) {
			PageData integral = new PageData();
			integral.put("U_ID", pd.get("U_ID"));
			integral.put("STATE", "00");
			integral.put("INTEGRAL_TOTAL", 0);
			result.put("integralPd", integral);
		}else{
			result.put("integralPd", integralPd);
		}
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, result);
		log.info("返回结果==" + results.toString());
		return results;
	}
	
}
