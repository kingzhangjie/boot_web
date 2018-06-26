package com.zj.boot_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.mapper.IntegralMapper;

@Service
public class IntegralService {

	@Autowired
    private IntegralMapper mapper;
	
	
	/*-----------------------微信积分模块  begin---------------------------*/

	/*
	 * 查询用户积分（必须参数：U_ID）
	 */
	public PageData selectWechatIntegralByUId (PageData pd) {
		
		return mapper.selectWechatIntegralByUId(pd);
	}

	/*
	 * 获取用户积分详细列表 （必须参数：U_ID）
	 */
	public List<PageData> selectWechatIntegralDetailList (PageData pd) {
		
		return mapper.selectWechatIntegralDetailList(pd);
	}
	
	
	/*-----------------------微信积分模块  end---------------------------*/
}
