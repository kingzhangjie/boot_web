package com.zj.boot_web.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.entity.AccessToken;
import com.zj.boot_web.common.entity.JsapiTicket;

public class WeChat {
	
	private static Logger logger = LoggerFactory.getLogger(WeChat.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	//public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		// 获取公众号access_token的链接
		String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = Http.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (!ComUtil.isEmpty(jsonObject)) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresInt(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				logger.error("获取token失败 errcode:{} errmsg:{}" + 
						jsonObject.getIntValue("errcode") + 
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取jsapi_ticket
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static JsapiTicket getJsapiTicket(String accessToken) {
		// 获取公众号jsapi_ticket的链接
		String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		JsapiTicket jsapiticket = null; // ticket分享值
		if (!ComUtil.isEmpty(accessToken)) {
			String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN",
					accessToken);
			JSONObject jsonObject = Http.httpsRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (!ComUtil.isEmpty(jsonObject)) {
				try {
					jsapiticket = new JsapiTicket();
					jsapiticket.setTicket(jsonObject.getString("ticket"));
					jsapiticket.setExpiresInt(jsonObject.getIntValue("expires_in"));
				} catch (JSONException e) {
					jsapiticket = null;
					// 获取ticket失败
					logger.error("获取ticket失败 errcode:{} errmsg:{}" + 
							jsonObject.getIntValue("errcode") + 
							jsonObject.getString("errmsg"));
				}
			}
		} else {
			logger.error("*****token为空 获取ticket失败******");
		}

		return jsapiticket;
	}
	
}
