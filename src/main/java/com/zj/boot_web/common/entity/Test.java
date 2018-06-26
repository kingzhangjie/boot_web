package com.zj.boot_web.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.OAuth2;

public class Test {
	
	private static final String weChatAppID = Const.weChatAppID;
	private static final String weChatAppSecret = Const.weChatAppSecret;
	
	private static final OAuth2 oAuth2 = new OAuth2(); //

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//JSONObject accessToken = oAuth2.getAccessToken(weChatAppID, weChatAppSecret);
		
		String long_url = "http://wx.xingxingbaobei.net/wx/woc/we-ua?en=1000002&ty=product&auth=2";
		//String access_token = accessToken.getString("access_token");
		String access_token = "11_iUjv_OSlRvIxqn3PpEhbWV6zw1tpZ3vuq27vWhyXv5Y4MNCCj1tLcDKsL0PiMzl0_2GZVmQmwI_UR3NOuHYg1yVSKoIdPseaQ7gW7NGOPo5pMsa2XbTjZVuxbMqisfQp1Wu67tzItThWkixzEVFdAFATKU";
		
		JSONObject object = oAuth2.getShortUrl(long_url, access_token);
		
		System.out.println(object.toJSONString());
		
	}

	private static OrderParamCheck getCheck() {
		OrderParamCheck check = new OrderParamCheck();
		check.setAppliName(null);
		check.setAppliPhone("");
		check.setAppliIdNo("410225199411145817");
		check.setAppliAge(16);
		check.setAppliEmail("15000844436@163.com");
		
		check.setRecogName("王勇");
		check.setRecogIdNo("410225199411145817");
		check.setRecogAge(16);
		
		return check;
	
	}
	
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }
}
