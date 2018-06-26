package com.zj.boot_web.common.entity;

public class AccessToken {
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresInt;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresInt() {
		return expiresInt;
	}

	public void setExpiresInt(int expiresInt) {
		this.expiresInt = expiresInt;
	}
}
