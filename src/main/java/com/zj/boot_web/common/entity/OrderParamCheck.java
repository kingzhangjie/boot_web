package com.zj.boot_web.common.entity;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;

public class OrderParamCheck implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "投保人姓名不能为空")
	@Size(min = 2, message = "投保人姓名最少为两位字符")
	private String appliName; // 投保人姓名
	
	@NotNull(message = "投保人手机号码不能为空")
	@Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "投保人手机号码格式有误")
	private String appliPhone; // 投保人手机号码
	
	@NotNull(message = "投保人身份证号码不能为空")
	@Pattern(regexp = "^\\d{15}$|^\\d{17}[0-9Xx]$", message = "投保人身份证号码格式不合法")
	private String appliIdNo; // 投保人身份证号码
	
	@Min(value = 16, message = "投保人年龄应大于16周岁")
	private int appliAge; // 投保人年龄
	
	@NotNull(message = "投保人电子邮箱不能为空")
	@Email(message = "投保人电子邮箱的格式不合法")
	private String appliEmail; // 投保人电子邮箱
	
	@NotNull(message = "被保人姓名不能为空")
	@Size(min = 2, message = "被保人姓名最少为两位字符")
	private String recogName; // 被保人姓名
	
	@NotNull(message = "被保人身份证号码不能为空")
	@Pattern(regexp = "^\\d{15}$|^\\d{17}[0-9Xx]$",message = "被保人身份证号码格式不合法")
	private String recogIdNo; // 被保人身份证号码
	
	
	@Range(min = 16, max = 65, message = "被保人年龄应该在[16，65]周岁之间")
	private int recogAge; // 被保人年龄
	

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	public String getAppliPhone() {
		return appliPhone;
	}

	public void setAppliPhone(String appliPhone) {
		this.appliPhone = appliPhone;
	}

	public String getAppliIdNo() {
		return appliIdNo;
	}

	public void setAppliIdNo(String appliIdNo) {
		this.appliIdNo = appliIdNo;
	}

	public int getAppliAge() {
		return appliAge;
	}

	public void setAppliAge(int appliAge) {
		this.appliAge = appliAge;
	}

	public String getAppliEmail() {
		return appliEmail;
	}

	public void setAppliEmail(String appliEmail) {
		this.appliEmail = appliEmail;
	}

	public String getRecogName() {
		return recogName;
	}

	public void setRecogName(String recogName) {
		this.recogName = recogName;
	}

	public String getRecogIdNo() {
		return recogIdNo;
	}

	public void setRecogIdNo(String recogIdNo) {
		this.recogIdNo = recogIdNo;
	}

	public int getRecogAge() {
		return recogAge;
	}

	public void setRecogAge(int recogAge) {
		this.recogAge = recogAge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
