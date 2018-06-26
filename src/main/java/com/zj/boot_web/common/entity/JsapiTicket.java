package com.zj.boot_web.common.entity;

public class JsapiTicket {
	// 获取到的凭证 
    private String ticket; 
    // 凭证有效时间，单位：秒 
    private int expiresInt; 
    
    public String getTicket() {
	    return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
 
   	public int getExpiresInt() { 
        return expiresInt; 
	} 
 
	public void setExpiresInt(int expiresInt) { 
	    this.expiresInt = expiresInt; 
    } 
}
