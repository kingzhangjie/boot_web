package com.zj.boot_web.controller.wechat;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.BaseController;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.base.PublicResult;
import com.zj.boot_web.common.base.PublicResultConstant;
import com.zj.boot_web.common.base.Threads;
import com.zj.boot_web.common.entity.VerifyCode;
import com.zj.boot_web.common.utils.ALiSms;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.DateUtil;
import com.zj.boot_web.service.IntegralService;
import com.zj.boot_web.service.UserService;

@Controller
@RequestMapping("/api/wx/user/")
public class UserApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(UserApiController.class);
	
	private static final String URL = Const.URL;
	private static final String CDN_URL = Const.CDN_URL;
	
	//二维码存放目录
	public static String fileUploadPath = Const.FILE_UPLOAD_DIR; // 文件上传目录
	
	public static final String PREFIX_PATH = "/api/wx/user/"; // 接口前缀路径
	
	//编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

	@Autowired
	private UserService userService;
	
	@Autowired
	private IntegralService integralService;
	
	@Log(description="接口请求",type="api")
	@PostMapping("binding.action")
	@Param(value="phone,sms_code")
	@ResponseBody
	public PublicResult<PageData> binding() throws Exception {
		log.info("=====================================请求绑定手机号接口:" + PREFIX_PATH + "binding.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		PageData userPd = this.getUserSession(); // 获取用户session信息
		if (ComUtil.isEmpty(userPd)) {
			results = new PublicResult<PageData>(
					PublicResultConstant.ACCREDIT_FAILURE, null);
		} else if (!ComUtil.isEmpty(userPd.get("U_PHONE"))) {
			results = new PublicResult<PageData>(
					PublicResultConstant.PHONE_ALREADY_IN, null);
		} else {
			VerifyCode smsCodeBean = (VerifyCode)this.getRequest().getSession().getAttribute("smsCodeBean");
			if (!ComUtil.isEmpty(smsCodeBean)) {
				String phone = smsCodeBean.getReceiveNumber();
				String code = smsCodeBean.getCode();
				String u_phone = pd.get("phone").toString();
				String sms_code = pd.get("sms_code").toString();
				if (ComUtil.equals(u_phone, phone) && ComUtil.equals(sms_code, code)) {
					boolean isTimeout = smsCodeBean.isTimeout(); //是否过期
					if (isTimeout) {
						results = new PublicResult<PageData>(
								PublicResultConstant.CAPTCHA_FAILURE, null);
					} else {
						PageData user = this.userService.selectWechatUserByPhone(pd);
						if (ComUtil.isEmpty(user)) {
							user = new PageData();
							String id = userPd.getString("ID");
							String u_encoding = userPd.getString("U_ENCODING");
							user.put("id", id);
							user.put("U_NAME", pd.get("name"));
							user.put("U_PHONE", u_phone);
							user.put("U_EMAIL", pd.get("email"));
							String date = DateUtil.getYyyyMM();
					        String uploadPath = "fuao/document/upload/qr/" + date + "/";
					        user.put("U_QR_URL", CDN_URL + "/" + uploadPath + "/" + u_encoding + ".jpg");
							Integer o = this.userService.updateWechatUserById(user);
							if (o > 0) {
								String userType = userPd.getString("U_TYPE");
								if (ComUtil.equals(userType, "T00201")) {
									String filePath = fileUploadPath + "WeChatQRcode\\";//二维码中镶嵌的logo地址
									String redirect = URL + "/wx/index.html";
									StringBuilder builder = new StringBuilder(URL + "/api/wx/oauth2/authorize.action?scope=snsapi_userinfo");
									builder.append("&en=" + userPd.getString("UID"));
									builder.append("&fen=" + userPd.getString("F_UID"));
									builder.append("&redirect=" + URLEncoder.encode(redirect, "UTF-8"));
									String longUrl = builder.toString();
									PageData data = new PageData();
									data.put("destPath", filePath);
									data.put("fileName", u_encoding);
									data.put("long_url", longUrl);
									data.put("uploadPath", uploadPath);
									//data.put("", "");
									Thread thread = new Threads(null, data, 100);
				                    thread.start();
								}
								
								this.removeSession("smsCodeBean");
								userPd.put("U_NAME", user.get("U_NAME"));
								userPd.put("U_PHONE", u_phone);
								userPd.put("U_EMAIL", user.get("U_EMAIL"));
								this.setUserSession(userPd); // 存储用户信息
								results = new PublicResult<PageData>(
										PublicResultConstant.SUCCESS, null);
							}
						} else {
							results = new PublicResult<PageData>(
									PublicResultConstant.PHONE_ALREADY_IN, null);
						}
					}
				} else {
					results = new PublicResult<PageData>(
							PublicResultConstant.CAPTCHA_ERROR, null);
				}
			} else {
				results = new PublicResult<PageData>(
						PublicResultConstant.OVERTIME_ERROR, null);
			}
		}
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	@Log(description="接口请求",type="api")
	@PostMapping("sendSmsCode.action")
	@Param(value="phone,img_code")
	@ResponseBody
	public PublicResult<PageData> sendSmsCode() throws Exception {
		log.info("=====================================请求获取短信验证码接口:" + PREFIX_PATH + "sendSmsCode.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult(PublicResultConstant.ERROR, null);
		PageData userPd = this.getUserSession(); // 获取用户session信息
		if (ComUtil.isEmpty(userPd)) {
			results = new PublicResult<PageData>(
					PublicResultConstant.ACCREDIT_FAILURE, null);
		} else if (!ComUtil.isEmpty(userPd.get("U_PHONE"))) {
			results = new PublicResult<PageData>(
					PublicResultConstant.PHONE_ALREADY_IN, null);
		} else {
			VerifyCode imgCodeBean = (VerifyCode)this.getRequest().getSession().getAttribute("imgCodeBean");
			if (!ComUtil.isEmpty(imgCodeBean)) {
				String code = imgCodeBean.getImgCode();
				String img_code = pd.get("img_code").toString();
				if (ComUtil.equals(img_code, code)) {
					boolean isTimeout = imgCodeBean.isTimeout(); //是否过期
					if (isTimeout) {
						PublicResultConstant.CAPTCHA_FAILURE.setMsg("图片验证码已失效");
						results = new PublicResult<PageData>(
								PublicResultConstant.CAPTCHA_FAILURE, null);
					} else {
						String u_phone = pd.get("phone").toString();
						//要发送的手机号
				        String phone = URLEncoder.encode(u_phone,ENCODING);
				        //生成六位随机数字phone
				        int suiji = (int)((Math.random()*9+1)*100000);
				        String verifycode = Integer.toString(suiji);
				        String templateCode = "SMS_109540051";
				        PageData result = new PageData();
				        result.put("verifyCode", verifycode);
				    	String success = ALiSms.aLiYunSmsTemplate(templateCode, phone, result);
						if (success == "OK" || "OK".equals(success)) {
							this.removeSession("imgCodeBean");
							VerifyCode smsCodeBean = new VerifyCode(String.valueOf(phone),String.valueOf(verifycode), 900); //初始化对象，设定过期时间为900s
				    		this.getRequest().getSession().setAttribute("smsCodeBean", smsCodeBean);
							results = new PublicResult<PageData>(
									PublicResultConstant.SUCCESS, null);
						} else {
				    		PublicResultConstant.SUCCESS.setMsg("抱歉，短信验证码发送失败，请稍后重试");
				    		results = new PublicResult<PageData>(
				    				PublicResultConstant.ERROR, null);
						}
					}
				} else {
					results = new PublicResult<PageData>(
							PublicResultConstant.CAPTCHA_ERROR, null);
				}
			} else {
				results = new PublicResult<PageData>(
						PublicResultConstant.OVERTIME_ERROR, null);
			}
		}
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	
	/**
	 *MethodsName(方法名)：personalCenter
	 *Description(描述)：个人中心
	 * @param  @param request
	 * @param  @return
	 * @param  @throws Exception
	 * @return PublicResult<PageData>
	 * @author Adger
	 * @date 2018年6月25日 上午11:51:41
	 */
	@Log(description="接口请求",type="api")
	@PostMapping("personalCenter.action")
	@Param(value="id")
	@ResponseBody
	public PublicResult<PageData> personalCenter(HttpServletRequest request) throws Exception {
		log.info("=====================================请求查询个人中心接口:" + PREFIX_PATH + "personalCenter.action=====================================");
		PageData pd = new PageData();
		PageData result = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(PublicResultConstant.ERROR, null);
		//获取用户id
		pd.put("id", pd.get("id"));
		PageData userPd = this.userService.selectWechatUserById(pd);
		if (!ComUtil.isEmpty(userPd)) {
			PageData integral = new PageData();
			integral.put("U_ID", userPd.getString("ID"));
			integral.put("STATE", "00");
			PageData integralPd = this.integralService.selectWechatIntegralByUId(integral);
			//用户没有积分
			if (ComUtil.isEmpty(integralPd)) {
				integral.put("INTEGRAL_TOTAL", 0);
				result.put("integralPd", integral);
			}else{
				result.put("integralPd", integralPd);
			}
			result.put("userPd", userPd);
		}else{
			results = new PublicResult<PageData>(PublicResultConstant.INVALID_USER, pd);
		}
		results = new PublicResult<PageData>(
				PublicResultConstant.SUCCESS, result);
		log.info("返回结果==" + results.toString());
		return results;
	}
	
}
