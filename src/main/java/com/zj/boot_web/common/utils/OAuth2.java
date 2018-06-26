package com.zj.boot_web.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;


public class OAuth2 {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 默认授权请求URL
     */
    private String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 获取微信授权
    *MethodsName(方法名)：getOAuth2CodeUrl
    *Description(描述)：TODO 获取微信授权
    * @param appId 公众号appId
    * @param redirect_uri 授权成功后的回调地址
    * @param scope 授权类型（snsapi_base：用户无感知的静默授权，snsapi_userinfo：需用户手动同意授权的非静默授权）
    * @param state 自定义随机字符串，微信回调会携带此参数
    * @return String 授权地址
    * @author Adger
    * @date 2018年6月23日上午11:57:21
    *
     */
    public String getOAuth2CodeUrl(String appId, String redirect_uri, String scope, String state) {
    	try {
            return authorize_url + "?appid=" + appId + "&redirect_uri=" + URLEncoder.encode(redirect_uri, "UTF-8") + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }
    
    /**
     * 获取access_token
    *MethodsName(方法名)：getAccessToken
    *Description(描述)：TODO 获取access_token
    * @param appId 开发者ID
    * @param secret 开发者密码
    * @param code 为换取access_token的票据
    * @param state 自定义随机字符串，微信回调会携带此参数
    * @return JSONObject
    * @author Adger
    * @date 2018年6月23日下午2:10:27
    *
     */
    public JSONObject getAccessToken(String appId, String secret) throws Exception {
    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
    	JSONObject jsonToken = Http.httpsRequest(url, "GET", null);
    	
		return jsonToken;
    }
    
    public JSONObject getShortUrl(String long_url, String access_token) throws Exception {
    	String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + access_token;
    	JSONObject object = new JSONObject();
    	object.put("action", "long2short");
    	object.put("long_url", long_url);
    	
    	JSONObject jsonToken = Http.httpsRequest(url, "POST", object.toJSONString());
    	
		return jsonToken;
    }
    
    /**
     * 获取access_token
     *MethodsName(方法名)：getAccessToken
     *Description(描述)：TODO 获取access_token
     * @param appId 开发者ID
     * @param secret 开发者密码
     * @param code 为换取access_token的票据
     * @param state 自定义随机字符串，微信回调会携带此参数
     * @return JSONObject
     * @author Adger
     * @date 2018年6月23日下午2:10:27
     *
     */
    public JSONObject getAccessToken(String appId, String secret, String code) throws Exception {
    	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
    	JSONObject jsonToken = Http.httpsRequest(url, "GET", null);
    	
    	return jsonToken;
    }
    
    /**
     * 
    *MethodsName(方法名)：
    *Description(描述)：TODO 
    * @param accessToken 网页授权接口调用凭证
    * @param openId 用户唯一标识
    * @return JSONObject
    * @author Adger
    * @date 2018年6月23日下午2:19:37
    *
     */
    public JSONObject getUserInfo(String accessToken, String openId) throws Exception {
    	String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
    	JSONObject jsonUserInfo = Http.httpsRequest(url, "GET", null);
    	
    	return jsonUserInfo;
    }
}
