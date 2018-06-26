 package com.zj.boot_web.mapper;

import java.util.List;

import com.zj.boot_web.common.base.PageData;

public interface IntegralMapper {
	
	/*-----------------------微信用户积分模块  begin---------------------------*/
	
	/*
	 * 查询用户积分（必须参数：U_ID）
	 */
	PageData selectWechatIntegralByUId(PageData pd);
	
	/*
	 * 获取用户积分详细列表 （必须参数：U_ID）
	 */
	List<PageData> selectWechatIntegralDetailList(PageData pd);
	
	/*-----------------------微信用户积分模块  end---------------------------*/
}
