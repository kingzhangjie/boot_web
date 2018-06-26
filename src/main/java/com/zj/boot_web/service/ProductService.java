package com.zj.boot_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.mapper.ProductMapper;

@Service
public class ProductService {

	@Autowired
    private ProductMapper mapper;
	
	/* ------------------后台产品管理模块start----------------------- */
	
	/*
	 * 查询系统产品
	 */
	public List<PageData> selectAdminProductList (PageData pd) {
		
		return mapper.selectAdminProductList(pd);
	}
	
	/*
	 * 查询系统产品总数
	 */
	public PageData selectAdminProductCount (PageData pd) {
		
		return mapper.selectAdminProductCount(pd);
	}
	
	/*
	 * 查询系统产品详情，根据产品id（必填参数：id ）
	 */
	public PageData selectAdminProductById (PageData pd) {
		
		return mapper.selectAdminProductById(pd);
	}
	
	/*
	 * 添加系统产品
	 */
	public Integer insertAdminProduct (PageData pd) {
		
		return mapper.insertAdminProduct(pd);
	}
	

	/*
	 * 修改系统产品，根据产品id（必填参数：id ）
	 */
	public Integer updateAdminProductById (PageData pd) {
		
		return mapper.updateAdminProductById(pd);
	}
	
	/*
	 * 查询系统产品方案
	 */
	public List<PageData> selectAdminSchemeList (PageData pd) {
		
		return mapper.selectAdminSchemeList(pd);
	}
	
	/*
	 * 查询系统产品方案总数
	 */
	public PageData selectAdminSchemeCount (PageData pd) {
		
		return mapper.selectAdminSchemeCount(pd);
	}
	
	/* ------------------后台产品管理模块end----------------------- */
	
	/* ------------------保险公司模块start----------------------- */
	
	/*
	 * 查询系统保险公司数据
	 */
	public List<PageData> selectInsuranceCompanyList (PageData pd) {
		
		return mapper.selectInsuranceCompanyList(pd);
	}
	
	/*
	 * 查询系统保险公司总数
	 */
	public PageData selectInsuranceCompanyCount (PageData pd) {
		
		return mapper.selectInsuranceCompanyCount(pd);
	}

	/*
	 * 查询系统保险公司数据，根据状态
	 */
	public List<PageData> selectInsuranceCompanyListByState (PageData pd) {
		
		return mapper.selectInsuranceCompanyListByState(pd);
	}
	
	/*
	 * 添加系统保险公司
	 */
	public Integer insertInsuranceCompany (PageData pd) {
		
		return mapper.insertInsuranceCompany(pd);
	}
	
	/*
	 * 修改系统保险公司，根据保险公司id（必填参数：id ）
	 */
	public Integer updateInsuranceCompanyById (PageData pd) {
		
		return mapper.updateInsuranceCompanyById(pd);
	}
	
	/* ------------------保险公司模块end---------------------- */
	
	
	/* ------------------微信产品管理模块start----------------------- */
	
	
	/*
	 * 查询微信产品,根据产品状态（参数：List<String> we_state）
	 */
	public List<PageData> selectWechatProductList (PageData pd) {
		
		return mapper.selectWechatProductList(pd);
	}
	
	/*
	 * 查询微信产品详情，根据产品id（必填参数：id ）
	 */
	public PageData selectWechatProductById (PageData pd) {
		
		return mapper.selectWechatProductById(pd);
	}
	
	
	/*
	 * 查询微信产品详情，根据产品pid（必填参数：pid ）
	 */
	public PageData selectWechatProductByPid (PageData pd) {
		
		return mapper.selectWechatProductByPid(pd);
	}
	
	/*
	 * 查询微信产品详情，根据产品pid（必填参数：pid ）
	 */
	public PageData selectWechatSchemeByPidAndSid (PageData pd) {
		
		return mapper.selectWechatSchemeByPidAndSid(pd);
	}
	
	/*
	 * 查询微信产品方案
	 */
	public List<PageData> selectWechatSchemeListByPid (PageData pd) {
		
		return mapper.selectWechatSchemeListByPid(pd);
	}
	
	/*
	 * 根据产品ID查询产品产品常见问题和案例数据
	 */
	public List<PageData> selectCaseIssueListByPid (PageData pd) {
		
		return mapper.selectCaseIssueListByPid(pd);
	}
	
	/* ------------------微信产品管理模块end----------------------- */
}
