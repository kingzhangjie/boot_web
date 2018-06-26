package com.zj.boot_web.mapper;

import java.util.List;

import com.zj.boot_web.common.base.PageData;

public interface ProductMapper {
	
	/* ------------------后台产品管理模块start----------------------- */
	
	/*
	 * 查询系统产品
	 */
	List<PageData> selectAdminProductList(PageData pd);
	
	/*
	 * 查询系统产品总数
	 */
	PageData selectAdminProductCount(PageData pd);
	
	/*
	 * 查询系统产品详情，根据产品id（必填参数：id ）
	 */
	PageData selectAdminProductById(PageData pd);
	
	/*
	 * 添加系统产品
	 */
	Integer insertAdminProduct(PageData pd);
	
	
	/*
	 * 修改系统产品，根据产品id（必填参数：id ）
	 */
	Integer updateAdminProductById(PageData pd);
	
	/*
	 * 查询系统产品方案
	 */
	List<PageData> selectAdminSchemeList(PageData pd);
	

	/*
	 * 查询系统产品方案总数
	 */
	PageData selectAdminSchemeCount(PageData pd);
	
	/* ------------------后台产品管理模块end----------------------- */
	
	/* ------------------保险公司模块start----------------------- */
	
	
	/*
	 * 查询系统保险公司数据
	 */
	List<PageData> selectInsuranceCompanyList(PageData pd);

	
	/*
	 * 查询系统保险公司总数
	 */
	PageData selectInsuranceCompanyCount(PageData pd);
	
	/*
	 * 查询系统保险公司数据，根据状态
	 */
	List<PageData> selectInsuranceCompanyListByState(PageData pd);
	
	/*
	 * 添加系统保险公司
	 */
	Integer insertInsuranceCompany(PageData pd);
	

	/*
	 * 修改系统保险公司，根据保险公司id（必填参数：id ）
	 */
	Integer updateInsuranceCompanyById(PageData pd);
	
	/*------------------保险公司模块end----------------------*/
	
	
	/* ------------------微信产品管理模块start----------------------- */
	
	/*
	 * 查询微信产品
	 */
	List<PageData> selectWechatProductList(PageData pd);
	
	
	/*
	 * 查询微信产品详情，根据产品id（必填参数：id ）
	 */
	PageData selectWechatProductById(PageData pd);
	
	/*
	 * 查询微信产品详情，根据产品pid（必填参数：pid ）
	 */
	PageData selectWechatProductByPid(PageData pd);
	
	/*
	 * 查询微信产品方案详情，根据产品pid，方案id（必填参数：pid，sid ）
	 */
	PageData selectWechatSchemeByPidAndSid(PageData pd);

	/*
	 * 查询微信产品方案
	 */
	List<PageData> selectWechatSchemeListByPid(PageData pd);
	/*
	 * 根据产品ID查询产品产品常见问题和案例数据
	 */
	List<PageData> selectCaseIssueListByPid(PageData pd);
	/* ------------------微信产品管理模块end----------------------- */
}
