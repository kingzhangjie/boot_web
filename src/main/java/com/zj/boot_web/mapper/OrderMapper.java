package com.zj.boot_web.mapper;

import com.zj.boot_web.common.base.PageData;

public interface OrderMapper {
	
	/*-----------------------系统后台订单管理模块  begin---------------------------*/
	
	
	
	/*-----------------------系统后台订单管理模块  end---------------------------*/

	
	
	/*-----------------------微信订单管理模块  begin---------------------------*/
	
	/*
	 * 添加微信个单订单数据
	 */
	Integer insertWechatOrder(PageData pd);
	
	/*
	 * 添加微信个单订单详情数据
	 */
	Integer insertWechatOrderGdDetail(PageData pd);
	
	/*
	 * 添加微信投保人数据
	 */
	Integer insertWechatApplicant(PageData pd);
	
	/*
	 * 添加微信被保人数据
	 */
	Integer insertWechatRecognizee(PageData pd);
	
	/*
	 * 添加微信受益人数据
	 */
	Integer insertWechatFavoree(PageData pd);
	
	
	/*-----------------------微信订单管理模块  end---------------------------*/
}
