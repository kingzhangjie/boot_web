package com.zj.boot_web.controller.wechat;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.BaseController;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.DateUtil;
import com.zj.boot_web.common.utils.MD5;
import com.zj.boot_web.common.utils.OAuth2;
import com.zj.boot_web.common.utils.Uuid;
import com.zj.boot_web.service.UserService;

@Controller
@RequestMapping("/api/wx/oauth2/")
public class OAuth2ApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(OAuth2ApiController.class);
	
	public static final String PREFIX_PATH = "/api/wx/oauth2/"; // 接口前缀路径
	
	private static final String weChatAppID = Const.weChatAppID;
	private static final String weChatAppSecret = Const.weChatAppSecret;
    
    public static Map<String,String> parameter; //用来存储订单的交易状态(key:订单号，value:致维商城地址
    
    private static final OAuth2 oAuth2 = new OAuth2(); //
    
    private String _state;

	@Autowired
	private UserService userService;
	
	
	@Log(description="接口请求",type="api")
	@GetMapping("authorize.action")
	@Param(value="scope,redirect")
	public ModelAndView authorize() throws Exception {
		log.info("=====================================请求微信公众号授权接口:" + PREFIX_PATH + "authorize.action=====================================");
		ModelAndView model = new ModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		
		if (ComUtil.isEmpty(parameter)) {
			parameter = new HashMap<String, String>();
		}
		pd.put("time_stamp", DateUtil.getTimeStamp());
		JSONObject object = (JSONObject) JSONObject.toJSON(pd);
		String parameters = object.toJSONString();
		String key = MD5.md5(weChatAppID + parameters);
		parameter.put(key, parameters);
		//PageData userPd = this.getSession(SESSION_NAME);
		
		String scope = pd.get("scope").toString();
		String redirect_uri = URL + "/fitness1.1/api/wx/oauth2/getAuthorizeCode.action";
		_state = key;
		String url = oAuth2.getOAuth2CodeUrl(weChatAppID, redirect_uri, scope, _state);
		model.setViewName("redirect:" + url);
		log.info("请求微信授权url地址：" + url);
		return model;
	}
	
	@Log(description="接口请求",type="api")
	@GetMapping("getAuthorizeCode.action")
	@Param(value="code,state")
	public ModelAndView getAuthorizeCode() throws Exception {
		log.info("=====================================请求微信公众号授权回调获取code接口:" + PREFIX_PATH + "getAuthorizeCode.action=====================================");
		ModelAndView model = new ModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		String url = "";
		String code = pd.getString("code");
		String state = pd.getString("state");
		if (ComUtil.isEmpty(parameter) || ComUtil.isEmpty(parameter.get(state))) {
			// 应跳转至授权失败界面
		}
		String parameters = parameter.get(state);
		JSONObject object = JSONObject.parseObject(parameters);
		parameter.remove(state);
		log.info("用户同意授权，根据code换取access_token：-----------------------------------------------------------------");
		JSONObject accessToken = oAuth2.getAccessToken(weChatAppID, weChatAppSecret, code);
		log.info("获取到的access_token：" + accessToken);
		String openid = accessToken.getString("openid");
		PageData userPd = new PageData();
		userPd.put("OPENID", openid);
		
		if (ComUtil.equals(object.getString("scope"), "snsapi_userinfo")) {
			log.info("开始获取用户信息···");
			JSONObject userInfo = oAuth2.getUserInfo(accessToken.getString("access_token"), accessToken.getString("openid"));
			log.info("获取到的用户信息：" + userInfo);
			
			if (!ComUtil.isEmpty(userInfo.get("nickname"))) {
				userPd.put("U_NICKNAME", filterEmoji(userInfo.getString("nickname")));
			}
			userPd.put("U_HEADIMG_URL", ComUtil.isEmpty(userInfo.get("headimg_url")) ? "" : userInfo.get("headimg_url").toString());
			userPd.put("SUBSCRIBE", ComUtil.isEmpty(userInfo.get("subscribe")) ? "0" : userInfo.get("subscribe").toString());
			if (!ComUtil.isEmpty(userInfo.get("subscribe_time"))) {
				userPd.put("SUBSCRIBE_TIME", DateUtil.getTimeStampToDateTimes(userInfo.get("subscribe_time")+""));
			}
		}
		pd.put("openid", openid);
		PageData user = this.userService.selectWechatUserByOpenId(pd);
		if (ComUtil.isEmpty(user)) { // 新用户
			String id = Uuid.get32UUID();//生成随机id编号
			String encoding = id.substring(0,2) + id.substring(id.length() - 7,id.length());
			userPd.put("ID", id);
			userPd.put("U_ENCODING", encoding.toUpperCase());
			userPd.put("F_UID", ComUtil.isEmpty(object.get("en")) ? "1000003" : object.get("en").toString());
			userPd.put("FF_UID", ComUtil.isEmpty(object.get("fen")) ? "1000002" : object.get("fen").toString());
			userPd.put("CHANNEL", ComUtil.isEmpty(object.get("ch")) ? "fuao" : object.get("ch").toString());
			userPd.put("u_type", ComUtil.isEmpty(object.get("u_type")) ? "T00301" : object.get("u_type").toString()); // 用户类型，默认为普通用户
			userPd.put("U_CATEGORY", "PERSONAGE"); //用户的类别（COMPANY：企业用户、 PERSONAGE：个人用户 、VIRTUAL：虚拟用户）
			userPd.put("U_TYPE", "T00301"); //普通用户
			userPd.put("CLEARING_PATTERN", "CPZX"); //结算模式
			userPd.put("SOURCE", "WeChat"); //用户来源（PC、WeChat、APP、WAP）
			userPd.put("OAUTH_TIME", DateUtil.getDate_Times()); // 首次授权访问时间
			userPd.put("STATE", "01");
			
			if (ComUtil.isEmpty(userPd.get("U_NICKNAME"))) {
				//生成四位随机数字
		        int suiji = (int)((Math.random()*9+1)*1000);
		        //如昵称为空则设置默认昵称
		        userPd.put("U_NICKNAME", "游客" + suiji);
			}
			
			Integer o = this.userService.insertWechatUser(userPd);
			if (o > 0) {
				this.setUserSession(userPd); // 存储用户信息
				logger.info("用户创建成功，用户信息已存入session:" + userPd.toString());
			}
		} else {
			if (ComUtil.isEmpty(userPd.get("U_NICKNAME")) || ComUtil.equals(user.get("U_NICKNAME").toString(), userPd.get("U_NICKNAME").toString())) {
				userPd.put("U_NICKNAME", "");
			} else {
				user.put("U_NICKNAME", userPd.get("U_NICKNAME").toString());
			}
			if (ComUtil.isEmpty(userPd.get("U_HEADIMG_URL")) || ComUtil.equals(user.get("U_HEADIMG_URL").toString(), userPd.get("U_HEADIMG_URL").toString())) {
				userPd.put("U_HEADIMG_URL", "");
			} else {
				user.put("U_HEADIMG_URL", userPd.get("U_HEADIMG_URL").toString());
			}
			userPd.put("LOGIN_TIME", DateUtil.getDate_Times());
			userPd.put("id", user.get("ID").toString());
			Integer o = this.userService.updateWechatUserById(userPd);
			this.setUserSession(user); //存储用户信息
			//this.getRequest().getSession().setAttribute(SESSION_NAME,  user);//保存session中
			if (o > 0) {
				logger.info("用户信息修改成功，修改后用户信息已存入session:" + user.toString());
			} else {
				logger.info("用户信息已存入session:" + user.toString());
			}
		}
		url = object.getString("redirect");
		model.setViewName("redirect:" + url);
		log.info("请求微信授权回调后重定向地址：" + url);
		return model;
	}
	
	public static String filterEmoji(String source) {
		if (source != null) {
			Pattern emoji;
			String verify = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
			emoji = Pattern.compile(verify,Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("?");
				return source;
			}
			return source;
		}
		return source;
	}
}
