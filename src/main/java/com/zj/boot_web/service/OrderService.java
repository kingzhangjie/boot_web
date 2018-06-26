package com.zj.boot_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.mapper.OrderMapper;

@Service
public class OrderService {

	@Autowired
    private OrderMapper mapper;
	
	
	/*-----------------------系统后台订单管理模块  begin---------------------------*/
	
	
	
	/*-----------------------系统后台订单管理模块  end---------------------------*/

	
	
	/*-----------------------微信订单管理模块  begin---------------------------*/
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer insertWechatOrder(PageData orderPd, PageData gdDetail, PageData appliPd, PageData recogPd, PageData favoreePd) {
		Integer o = 0;
		o = mapper.insertWechatOrder(orderPd);
		o = mapper.insertWechatOrderGdDetail(gdDetail);
		o = mapper.insertWechatApplicant(appliPd);
		o = mapper.insertWechatRecognizee(recogPd);
		if (!ComUtil.isEmpty(favoreePd)) {
			o = mapper.insertWechatFavoree(favoreePd);
		}
		return o;
	}
	
	
	/*-----------------------微信订单管理模块  end---------------------------*/
}
