package com.zj.boot_web.common.entity;

import java.io.Serializable;

public class VerifyCode implements Serializable {
	private static final long serialVersionUID = 1L;

	// 接收验证码的手机号或者邮箱账号
	private String receiveNumber;
	// 验证码
	private String code;
	// 图片验证码
	private String imgCode;
	// 过期的时间 秒
	private int timeout;
	// 创建的时间
	private long createTime;
	
	
	public VerifyCode(String receiveNumber, String code, int timeout) {
		super();
		this.receiveNumber = receiveNumber;
		this.code = code;
		this.timeout = timeout;
		createTime = System.currentTimeMillis();
	}
	
	public VerifyCode(String imgCode, int timeout) {
		super();
		this.imgCode = imgCode;
		this.timeout = timeout;
		createTime = System.currentTimeMillis();
	}

	public String getReceiveNumber() {
		return receiveNumber;
	}

	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean isTimeout(){
		return (System.currentTimeMillis()-getCreateTime())>=(getTimeout()*1000);
	}
}
