package com.zj.boot_web.controller.admin;

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
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.Tool;
import com.zj.boot_web.service.ProductService;

@Controller
@RequestMapping("/api/admin/product/")
public class SysProductApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(SysProductApiController.class);
	
	public static final String PREFIX_PATH = "/api/admin/product/"; // 接口前缀路径

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
	@Log(description="接口请求",type="api",userType="admin")
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
		pd.putAll(Tool.getPageAndSize(pd));
		List<PageData> productList = this.productService.selectAdminProductList(pd);
		
		Object count = this.productService.selectAdminProductCount(pd).get("count");
		
		
		pd.clear();
		pd.put("data", productList);
		pd.put("count", count);
		//pd.put("msg", "加载成功");
		pd.put("code", 0); 
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, pd);
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	/**
	 * 添加产品接口
	*MethodsName(方法名)：insertProduct
	*Description(描述)：TODO 添加产品接口
	* @param  @return
	* @param  @throws 
	* @return PublicResult<PageData>
	* @author Adger
	* @date 2018年6月21日下午4:54:18
	*
	 */
	@Log(description="接口请求",type="api",userType="admin")
	@PostMapping("insertProduct.action")
	@ResponseBody
	public PublicResult<PageData> insertProduct(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求添加产品接口:" + PREFIX_PATH + "insertProduct.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		
		
		Integer o = this.productService.insertAdminProduct(pd);
		if (o > 0) {
			results = new PublicResult<PageData>(
					PublicResultConstant.SUCCESS, null);
		}
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
	@Log(description="接口请求",type="api",userType="admin")
	@PostMapping("selectProductById.action")
	@Param(value="id")
	@ResponseBody
	public PublicResult<PageData> selectProductById(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求查询产品方案列表接口:" + PREFIX_PATH + "selectProductById.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		List<PageData> schemeList = this.productService.selectAdminSchemeList(pd);
		
		Object count = this.productService.selectAdminSchemeCount(pd).get("count");
		
		pd.clear();
		pd.put("data", schemeList);
		pd.put("count", count);
		pd.put("code", 0); 
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, pd);
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	
	
	/**
	 * 修改产品接口根据产品id
	*MethodsName(方法名)：updateProductById
	*Description(描述)：TODO 修改产品接口根据产品id
	* @param  @return
	* @param  @throws 
	* @return PublicResult<PageData>
	* @author Adger
	* @date 2018年6月21日下午4:55:58
	*
	 */
	@Log(description="接口请求",type="api",userType="admin")
	@PostMapping("updateProductById.action")
	@Param(value="id")
	@ResponseBody
	public PublicResult<PageData> updateProductById(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求修改产品接口:" + PREFIX_PATH + "updateProductById.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		
		
		Integer o = this.productService.updateAdminProductById(pd);
		if (o > 0) {
			results = new PublicResult<PageData>(
					PublicResultConstant.SUCCESS, null);
		}
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	/**
	 * 查询产品方案接口
	*MethodsName(方法名)：selectSchemeList
	*Description(描述)：TODO 查询产品方案接口
	* @param  @return
	* @param  @throws 
	* @return PublicResult<PageData>
	* @author Adger
	* @date 2018年6月21日下午5:22:04
	*
	 */
	@Log(description="接口请求",type="api",userType="admin")
	@PostMapping("selectSchemeList.action")
	@Param(value="pid")
	@ResponseBody
	public PublicResult<PageData> selectSchemeList(HttpServletRequest request)
			throws Exception {
		log.info("=====================================请求查询产品方案列表接口:" + PREFIX_PATH + "selectSchemeList.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		pd.putAll(Tool.getPageAndSize(pd));
		List<PageData> schemeList = this.productService.selectAdminSchemeList(pd);
		
		Object count = this.productService.selectAdminSchemeCount(pd).get("count");
		
		pd.clear();
		pd.put("data", schemeList);
		pd.put("count", count);
		pd.put("code", 0); 
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, pd);
		log.info("返回结果==" + results.toString());
		return results;
	}
}
