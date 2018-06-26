package com.zj.boot_web.controller.wechat;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.BaseController;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.base.PublicResult;
import com.zj.boot_web.common.base.PublicResultConstant;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.service.ProductService;

@Controller
@RequestMapping("/api/wx/product/")
public class ProductApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(ProductApiController.class);
	
	public static final String PREFIX_PATH = "/api/wx/product/"; // 接口前缀路径

	@Autowired
	private ProductService productService;
	
	/**
	 * 请求查询产品列表接口
	*MethodsName(方法名)：selectProductList
	*Description(描述)：TODO 请求查询产品列表接口
	* @param  @return
	* @param  @throws 
	* @return PublicResult<PageData>
	* @author Adger
	* @date 2018年6月21日下午4:41:26
	*
	 */
	@Log(description="接口请求",type="api")
	@PostMapping("selectProductList.action")
	@ResponseBody
	public PublicResult<PageData> selectProductList(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求查询产品列表接口:" + PREFIX_PATH + "selectProductList.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		
		//PageData userPd = this.getUserPd("WeChatUser");
		PageData userPd = new PageData();
		userPd.put("NICKNAME", "Wong ²⁰¹⁸");
		userPd.put("NAME", "Wong ²⁰¹⁸");
		userPd.put("OPENID", "ooMj6wMjtIE09cfqRSHaOGOQ2zy5");
		userPd.put("U_PHONE", "15000844436");
		userPd.put("UID", "1201256");
		userPd.put("ID", "c38dfa886c2145aaac46322ab09c4d28");
		userPd.put("U_CATEGORY", "TEST");
		userPd.put("U_TYPE", "T00201");
		userPd.put("F_UID", "1000000");
		//this.getRequest().getSession().setAttribute(SESSION_NAME, userPd);//保存session中
		if (ComUtil.isEmpty(userPd)) {
			results = new PublicResult<PageData>(
					PublicResultConstant.ACCREDIT_FAILURE, null);
			return results;
		}
		String WE_STATE = "01,03";
		if (!ComUtil.isEmpty(userPd) && !ComUtil.isEmpty(userPd.get("U_CATEGORY"))) {
			String userType = userPd.getString("U_CATEGORY");
			if (userType == "TEST" || "TEST".equals(userType)) {//用户类型，00501为平台测试用户
				WE_STATE += ",02,04,05,06";//预上线状态
			}
		}
		List<String> WE_STATE2 = Arrays.asList(WE_STATE.split(","));
		pd.put("we_state", WE_STATE2);
		pd.put("p_type", "GR");
		
		List<PageData> productList = this.productService.selectWechatProductList(pd);
		pd.clear();
		pd.put("productList", productList);
		//pd.put("msg", "加载成功");
		pd.put("userName", userPd.get("NAME") == null ? "游客" : userPd.get("NAME").toString()); 
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, pd);
		log.info("返回结果==" + results.toString());
		return results;
	}

	
	/**
	 * 查询产品详情根据产品id
	*MethodsName(方法名)：selectProductById
	*Description(描述)：TODO 查询产品详情根据产品id
	* @param  @return
	* @param  @throws 
	* @return PublicResult<PageData>
	* @author Adger
	* @date 2018年6月22日上午8:57:14
	*
	 */
	@Log(description="接口请求",type="api")
	@PostMapping("selectProductById.action")
	@Param(value="id")
	@ResponseBody
	public PublicResult<PageData> selectProductById(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求查询产品详情接口:" + PREFIX_PATH + "selectProductById.action=====================================");
		PageData pd = new PageData();
		PageData result = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		pd.put("pid", pd.get("id"));
		PageData product = this.productService.selectWechatProductById(pd);
		List<PageData> schemeList = this.productService.selectWechatSchemeListByPid(pd);
		if (!ComUtil.isEmpty(schemeList)) {
			product.put("SIZE", schemeList.size());
			//List<PageData> dutyList = new ArrayList<>();
			for (int i = 0; i < schemeList.size(); i++) {
				PageData safeguardDuty = new PageData();
				safeguardDuty.put("sid", schemeList.get(i).getString("ID"));
				JSONArray array = JSONArray.parseArray(schemeList.get(i).getString("SAFEGUARD_DUTY"));
				
				schemeList.get(i).put("SAFEGUARD_DUTYS", array);
				if (i == 0) {
					result.put("duty1", array);
				}
				//dutyList.add(safeguardDuty);
				String name = schemeList.get(i).getString("S_NAME");
				name = name.replace("，", ",");
				String[] names = name.split(",");
				schemeList.get(i).put("NAME1", names[0]);
				if (names.length > 1) {
					schemeList.get(i).put("NAME2", names[1]);
				}
				
				schemeList.get(i).put("SUMS", sumCoverage(schemeList.get(i).get("SUM_COVERAGE").toString()));
				
			}
			if (!ComUtil.isEmpty(product.get("FILE_URLS"))) {
				JSONArray array = JSONArray.parseArray(product.getString("FILE_URLS"));
				product.put("FILE_URLS", array);
			}
			
			List<PageData> caseIssueList = this.productService.selectCaseIssueListByPid(pd);
			
			result.put("schemeList", schemeList);
			//result.put("dutyList", dutyList);
			result.put("caseIssueList", caseIssueList);
		}
		
		
		pd.clear();
		result.put("product", product);
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, result);
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	
	public static String sumCoverage(String sumCoverage) {
		int aa = Integer.parseInt(sumCoverage);
		float num = (float) aa / 10000;
		if (num > 1) {
			int bb = (int) num;
			if (num > bb) {
				sumCoverage = num + "万";
			} else {
				sumCoverage = bb + "万";
			}
		} else {
			num = (float) aa / 1000;
			if (num > 1) {
				int bb = (int) num;
				if (num > bb) {
					sumCoverage = num + "千";
				} else {
					sumCoverage = bb + "千";
				}
			}
		}
		return sumCoverage;
	}
}
