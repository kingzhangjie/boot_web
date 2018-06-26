package com.zj.boot_web.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zj.boot_web.common.base.PageData;

public class ALiSms {
	
	private static Logger log = LoggerFactory.getLogger(ALiSms.class);

	// 初始化ascClient需要的几个参数
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = Const.accessKeyId;// 速保网络科技子账号
	static final String accessKeySecret = Const.accessKeySecret;// 速保网络科技子账号
	

	/**
	 * 阿里云短信发送 MethodsName(方法名)：aLiYunSendSms Description(描述)：TODO 阿里云短信发送
	 * 
	 * @param @param phoneNum
	 *        必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟
	 *        ,验证码类型的短信推荐使用单条调用的方式
	 * @param @param templateCode 必填:短信模板-可在短信控制台中找到
	 * @param @param templateParam 可选:模板中的变量替换JSON串
	 * @param @return OK:发送成功
	 * @param @throws ClientException 请求失败会抛ClientException异常
	 * @return String 返回类型
	 * @author Adger
	 * @date 2017年11月5日 下午6:21:13
	 */
	public static String aLiYunSendSms(String phoneNum, String templateCode,
			String templateParam) throws ClientException {

		// 设置超时时间-可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-shanghai",
				accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", product,
				domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phoneNum);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("星星保呗");
		// 必填:短信模板-可在短信控制台中找到
		// request.setTemplateCode("SMS_109450050");
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		// request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
		if (templateParam != "") {
			request.setTemplateParam(templateParam);
		}

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		long time = System.currentTimeMillis();
		String timeStamp = Long.toString(time);
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(timeStamp);

		// hint 此处可能会抛出异常，注意catch 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		String _code = "";

		String content = "";
		try {

			sendSmsResponse = acsClient.getAcsResponse(request);

			if (sendSmsResponse.getCode() != null
					&& sendSmsResponse.getCode().equals("OK")) {
				// 请求成功
				log.error("短信发送请求成功");
				// System.out.println("短信接口返回的数据----------------");
				// System.out.println("Code=" + sendSmsResponse.getCode());
				// System.out.println("Message=" +
				// sendSmsResponse.getMessage());
				// System.out.println("RequestId=" +
				// sendSmsResponse.getRequestId());
				// System.out.println("BizId=" + sendSmsResponse.getBizId());
				_code = sendSmsResponse.getCode();
				content = "Phone=" + phoneNum + "(" + templateCode + ")"
						+ ",Code=" + _code + ",Message="
						+ sendSmsResponse.getMessage() + ",RequestId="
						+ sendSmsResponse.getRequestId() + ",BizId="
						+ sendSmsResponse.getBizId() + ";";
			} else {
				_code = sendSmsResponse.getCode();
				content = "Phone=" + phoneNum + "(" + templateCode + ")"
						+ ",Code=" + _code + ",Message="
						+ sendSmsResponse.getMessage() + ",RequestId="
						+ sendSmsResponse.getRequestId() + ",BizId="
						+ sendSmsResponse.getBizId() + ";";
			}
			log.info(content);
			//FileUtil.writeFileWeeklyLog("aliyunSendSmsLog", "sms", content);// 写入短信发送记录日志
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			_code = "NO";
			content = "Phone=" + phoneNum + "(" + templateCode + ")" + ",Code="
					+ _code + ",Message=短信发送请求失败";
			log.error("短信发送请求失败");
			log.error(content);
			//FileUtil.writeFileWeeklyLog("aliyunSendSmsLog", "sms", content);// 写入短信发送记录日志
			e.printStackTrace();
		}

		return _code;
	}

	public static String aLiYunSmsTemplate(String TemplateCode, String phone, PageData smsPd) throws ClientException {
		String _code = ""; // 发送状态
		String templateParam = ""; // 模板参数
		switch (TemplateCode) {
		case "SMS_109540051": // 验证码统一短信模板
			templateParam = "{\"code\":\"" + smsPd.get("verifyCode") + "\"}";
			break;
		case "SMS_109445098": // 购买成功通知统一短信模板
			templateParam = "{\"orderNo\":\"" + smsPd.get("orderNo") + "\"}";
			break;
		case "SMS_109495066": // 出单成功通知统一短信模板
			templateParam = "{\"userName\":\"" + smsPd.get("userName") + "\", \"productName\":\"" + smsPd.get("productName") + "\", \"policyNo\":\"" + smsPd.get("policyNo") + "\"}";
			break;
		case "SMS_109535080": // 保单到期提醒通知统一短信模板
			templateParam = "{\"productName\":\"" + smsPd.get("productName") + "\", \"days\":\"" + smsPd.get("days") + "\"}";
			break;
		case "SMS_109465097": // 赠险领取成功短信提醒通知统一短信模板
			System.out.println("2");
			break;
		case "SMS_109560086": // 投保信息错误提醒通知统一短信模板
			System.out.println("2");
			break;
		case "SMS_109400075": // 保单号错发通知统一短信模板
			System.out.println("2");
			break;
		case "SMS_109465099": // 人保产品下单成功通知统一短信模板
			templateParam = "{\"userName\":\"" + smsPd.get("userName") + "\", \"productName\":\"" + smsPd.get("productName") + "\", \"policyNo\":\"" + smsPd.get("policyNo") + "\"}";
			break;
		case "SMS_109490090": // 雇主责任险通知统一短信模板
			System.out.println("2");
			break;
		case "SMS_114395678": // 系统异常通知统一短信模板
			templateParam = "{\"explain\":\"" + smsPd.get("explain") + "\", \"content\":\"" + smsPd.get("msg") + "\"}";
			break;
		case "SMS_127156558": // 乐健产品下单成功通知统一短信模板
			templateParam = "{\"orderNo\":\"" + smsPd.get("orderNo") + "\", \"dayTime\":\"" + smsPd.get("dayTime") + "\"}";
			break;
		case "SMS_127154270": // 乐健产品出单成功通知统一短信模板
			templateParam = "{\"policyNo\":\"" + smsPd.get("policyNo") + "\"}";
			break;
		default:
			System.out.println("default");
			break;
		}
		System.out.println(templateParam);
		_code = aLiYunSendSms(phone, TemplateCode, templateParam);
		return _code;
	}
	
}
