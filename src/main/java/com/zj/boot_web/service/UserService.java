package com.zj.boot_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
    private UserMapper mapper;
	
	/*-----------------------系统后台用户管理模块  begin---------------------------*/
	
	/*
	 * 查询系统管理员，根据用户账号和密码查询（必须参数：account，password）
	 */
	public PageData seleteAdminUserByAccountAndPwd (PageData pd) {
		
		return mapper.seleteAdminUserByAccountAndPwd(pd);
	}
	
	/*
	 * 添加系统管理员
	 */
	public Integer insertAdminUser (PageData pd) {
		
		return mapper.insertAdminUser(pd);
	}
	

	/*
	 * 修改系统管理员,根据用户suid（必填参数：SUID）
	 */
	public Integer updateAdminUserBySuid (PageData pd) {
		
		return mapper.updateAdminUserBySuid(pd);
	}
	
	/*-----------------------系统后台用户管理模块  end---------------------------*/
	
	/*-----------------------微信用户管理模块  begin---------------------------*/
	

	/*
	 * 查询微信用户（必须参数：id）
	 */
	public PageData selectWechatUserById (PageData pd) {
		
		return mapper.selectWechatUserById(pd);
	}
	

	/*
	 * 查询微信用户（必须参数：openid）
	 */
	public PageData selectWechatUserByOpenId (PageData pd) {
		
		return mapper.selectWechatUserByOpenId(pd);
	}
	

	/*
	 * 查询微信用户（必须参数：phone）
	 */
	public PageData selectWechatUserByPhone (PageData pd) {
		
		return mapper.selectWechatUserByPhone(pd);
	}
	
	/*
	 * 添加微信用户
	 */
	public Integer insertWechatUser (PageData pd) {
		
		return mapper.insertWechatUser(pd);
	}
	
	/*
	 * 修改微信用户,根据用户id（必填参数：id）
	 */
	public Integer updateWechatUserById (PageData pd) {
		
		return mapper.updateWechatUserById(pd);
	}
	
	/*-----------------------微信用户管理模块  end---------------------------*/
}
