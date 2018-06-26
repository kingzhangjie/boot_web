 package com.zj.boot_web.mapper;


import com.zj.boot_web.common.base.PageData;

public interface UserMapper {
	
	/*-----------------------系统后台用户管理模块  begin---------------------------*/
	
	
	/*
	 * 查询系统管理员，根据用户账号和密码查询（必须参数：account，password）
	 */
	PageData seleteAdminUserByAccountAndPwd(PageData pd);
	
	
	/*
	 * 添加系统管理员
	 */
	Integer insertAdminUser(PageData pd);
	
	/*
	 * 修改系统管理员,根据用户suid（必填参数：suid）
	 */
	Integer updateAdminUserBySuid(PageData pd);
	
	/*-----------------------系统后台用户管理模块  end---------------------------*/
	
	/*-----------------------微信用户管理模块  begin---------------------------*/
	
	
	/*
	 * 查询微信用户（必须参数：id）
	 */
	PageData selectWechatUserById(PageData pd);
	
	/*
	 * 查询微信用户（必须参数：openid）
	 */
	PageData selectWechatUserByOpenId(PageData pd);
	
	/*
	 * 查询微信用户（必须参数：phone）
	 */
	PageData selectWechatUserByPhone(PageData pd);
	
	/*
	 * 添加微信用户
	 */
	Integer insertWechatUser(PageData pd);
	
	/*
	 * 修改微信用户,根据用户id（必填参数：id）
	 */
	Integer updateWechatUserById(PageData pd);
	
	/*-----------------------微信用户管理模块  end---------------------------*/
}
