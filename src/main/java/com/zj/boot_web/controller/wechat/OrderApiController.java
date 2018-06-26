package com.zj.boot_web.controller.wechat;

import java.text.DecimalFormat;
import java.text.ParseException;

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
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.DateUtil;
import com.zj.boot_web.common.utils.IDCard;
import com.zj.boot_web.common.utils.Tool;
import com.zj.boot_web.common.utils.Uuid;
import com.zj.boot_web.common.utils.Verify;
import com.zj.boot_web.service.ProductService;

@Controller
@RequestMapping("/api/wx/order/")
public class OrderApiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(OrderApiController.class);
	
	public static final String PREFIX_PATH = "/api/wx/order/"; // 接口前缀路径

	@Autowired
	private ProductService productService;
	
	
	@Log(description="接口请求",type="api")
	@PostMapping("placeAnOrder.action")
	@Param(value="phone,sms_code")
	@ResponseBody
	public PublicResult<PageData> placeAnOrder() throws Exception {
		log.info("=====================================请求下单接口:" + PREFIX_PATH + "placeAnOrder.action=====================================");
		PageData pd = new PageData();
		pd = getPageData();
		log.info("请求参数==" + pd);
		PublicResult<PageData> results = new PublicResult<PageData>(
				PublicResultConstant.ERROR, null);
		PageData userPd = this.getUserSession(); // 获取用户session信息
		if (ComUtil.isEmpty(userPd) || ComUtil.isEmpty(userPd.get("U_PHONE"))) { // 验证用户登录信息
			results = new PublicResult<PageData>(
					PublicResultConstant.UNAUTHORIZED, null);
		} else {
			if (Verify.orderParamChecksum(pd)) { // 验证请求参数是否合法
				//查询产品信息
				PageData productPd = this.productService.selectWechatProductById(pd);
				if (!ComUtil.isEmpty(productPd)) {
					//查询方案信息
					PageData schemePd = this.productService.selectWechatSchemeByPidAndSid(pd);
					if (!ComUtil.isEmpty(schemePd)) {
						String userId = userPd.getString("ID"); // 得到当前登录用户ID
						PageData order = new PageData();
						String recogId = Uuid.get32UUID();//生成被保人ID
						order.put("userId", userId);
						order.put("recogId", recogId);
						int min_age = Integer.parseInt(schemePd.getString("UNDERWRITE_MINIMUM_AGE"));
						int max_age = Integer.parseInt(schemePd.getString("UNDERWRITE_MAXIMUM_AGE"));
						PageData recogPd = recogPd(order, pd); // 被保人信息数据
						int age = Integer.parseInt(recogPd.getString("RECOG_AGE"));
						if (age < min_age || age > max_age) {
							PublicResultConstant.PARAM_ERROR.setMsg("被保人的年龄不在承保范围内，[年龄应为 "+min_age+"-"+max_age+" 周岁]");
							results = new PublicResult<PageData>(
									PublicResultConstant.PARAM_ERROR, null);
							log.info("返回结果==" + results.toString());
							return results;
						}
						
						String payPrice = schemePd.getString("PAY_PRICE"); // 得到该方案的价格
						float price = Float.valueOf(payPrice);
						if (price < 1) {
							payPrice = schemePd.getString("SHOW_CP");//得到该方案的价格
						}
						price = Float.valueOf(payPrice);
						float price2 = Float.valueOf(pd.get("total_price").toString());
						if (!ComUtil.equals(price, price2)) {
							results = new PublicResult<PageData>(
									PublicResultConstant.NETWORK_SECURITY_ERROR, null);
							log.info("返回结果==" + results.toString());
							return results;
						}
						DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
						String totalPrice = decimalFormat.format(price);//format 返回的是字符串
						
						String orderId = Uuid.get32UUID();//生成订单ID
						String detailId = Uuid.get32UUID();//生成订单明细ID
						String appliId= Uuid.get32UUID();//生成投保人ID
						String favoreeId = Uuid.get32UUID(); //受益人ID
						String number = productPd.getString("P_NUMBER"); // 产品编号
						String subNumber = number.substring(number.length() - 3,number.length()); // 截取得到编号后3位
						String orderNo = subNumber + Uuid.getOrderNo(); // 订单编号
						order.put("orderId", orderId);
						order.put("detailId", detailId);
						order.put("appliId", appliId);
						order.put("orderNo", orderNo);
						order.put("favoreeId", favoreeId);
						order.put("totalPrice", totalPrice);
						
						PageData orderPd = orderPd(order, userPd, productPd, schemePd, pd); // 订单数据
						PageData gdDetailPd = gdDetailPd(order, userPd, productPd, schemePd, pd); // 订单详情数据
						PageData favoreePd = new PageData(); // 保存受益人信息数据
						int favoree_type = Integer.parseInt(schemePd.getString("FAVOREE_TYPE")); // 获取受益人类型
						if (favoree_type == 1) {
							favoreePd = favoreePd(order, pd); // 受益人数据
							gdDetailPd.put("FAVOREE_ID", favoreeId); // 保存受益人id
						}
						PageData appliPd = appliPd(order, pd); // 投保人信息数据
						
					} else { // 方案不存在或已下架
						PublicResultConstant.PARAM_ERROR.setMsg("抱歉，您选择的方案不存在或已下架");
						results = new PublicResult<PageData>(
								PublicResultConstant.PARAM_ERROR, null);
					}
				} else { // 产品不存在或已下架
					PublicResultConstant.PARAM_ERROR.setMsg("抱歉，该产品不存在或已下架");
					results = new PublicResult<PageData>(
							PublicResultConstant.PARAM_ERROR, null);
				}
				
			} else { // 请求参数不合法
				results = new PublicResult<PageData>(
						PublicResultConstant.PARAM_ERROR, null);
			}
		}
		
		
		log.info("返回结果==" + results.toString());
		return results;
	}
	
	/**
	 * 订单数据
	*MethodsName(方法名)：orderPd
	*Description(描述)：TODO 订单数据
	* @param  @return
	* @param  @throws 
	* @return PageData
	* @author Adger
	* @date 2018年6月25日上午10:23:27
	*
	 */
	public PageData orderPd(PageData order, PageData userPd, PageData productPd, PageData schemePd, PageData pd) throws Exception {
		PageData orderPd = new PageData();
		//订单数据
		orderPd.put("ID", order.getString("orderId"));
		orderPd.put("ORDER_NO", order.getString("orderNo")); // 21位订单编号
		orderPd.put("USER_ID", userPd.getString("ID"));//用户ID
		orderPd.put("PRODUCT_ID", productPd.getString("ID"));//产品ID
		orderPd.put("PRODUCT_PID", productPd.getString("PID"));//产品PID
		orderPd.put("PRODUCT_PID", productPd.getString("PID"));//产品PID
		orderPd.put("PRODUCT_NO", productPd.getString("P_NUMBER"));//产品编号
		orderPd.put("PRODUCT_NAME", productPd.getString("P_NAME"));//产品名称
		
		orderPd.put("SCHEME_ID", schemePd.getString("ID"));//方案ID
		orderPd.put("SCHEME_NAME", schemePd.getString("S_NAME"));//方案名称
		orderPd.put("IC_ID", productPd.getString("IC_ID"));//保险公司ID
		orderPd.put("IC_NAME", productPd.getString("IC_NAME"));//保险公司名称ID
		String type= userPd.get("U_TYPE").toString();
		if ("T00201" == type || "T00201".equals(type)) {
			orderPd.put("RECO_UID", userPd.getString("UID"));//推荐人编码
			orderPd.put("F_RECO_UID", userPd.getString("F_UID"));//经销商编码
		} else if("T00101" == type || "T00101".equals(type)) {
			orderPd.put("RECO_UID", userPd.getString("UID"));//推荐人编码
			orderPd.put("F_RECO_UID", userPd.getString("UID"));//经销商编码
		} else {
			orderPd.put("RECO_UID", "1000003");//推荐人编码
			orderPd.put("F_RECO_UID", "1000002");//经销商编码
		}
		
		orderPd.put("APPLI_NAME", pd.getString("appli_name"));//投保人姓名
		orderPd.put("APPLI_PHONE", pd.getString("appli_phone"));//投保人手机号
		orderPd.put("APPLI_EMAIL", pd.getString("appli_email"));//投保人邮箱
		orderPd.put("INSURED_NAME", pd.getString("recog_name"));//被保人姓名
		String totalPrice = order.getString("totalPrice");
		orderPd.put("TOTAL_PRICE", totalPrice);//订单金额
		orderPd.put("NEED_PAY_PRICE", totalPrice);//需支付金额
		orderPd.put("SETTLEMENT_PREMIUM", totalPrice);//结算保费
		orderPd.put("AGREEMENT_PREMIUN", totalPrice);//协议保费
		String userType = userPd.getString("U_CATEGORY");//当前登录用户的类型
		if ("TEST".equals(userType) || "TEST" == userType) {//验证是否是平台测试用户
			orderPd.put("ORDER_TYPE", "9");//订单类型--1：个单、2：团单、3：企单、9：测试订单
		} else {
			orderPd.put("ORDER_TYPE", "1");//订单类型--1：个单、2：团单、3企单
		}
		orderPd.put("ORDER_MRK", "1");//订单性质--1：见费出单、2：月结
		//orderPd.put("INVOICE_STATE", "00");//订单发票状态--默认：00（未开票）
		orderPd.put("PAY_STATE", "02");//支付状态--默认：02（未支付）
		orderPd.put("SOURCE", "WeChat");//订单来源--默认：WeChat
		orderPd.put("ORDER_IDENDITY", pd.get("idendity"));//订单标识--默认：Normal
		orderPd.put("IS_PORT", productPd.getString("IS_PORT"));//是否对接 
		orderPd.put("CASHIER_DESK", productPd.getString("CASHIER_DESK"));//收银台
		
		return orderPd;
	}
	
	/**
	 * 订单详情数据
	*MethodsName(方法名)：gdDetailPd
	*Description(描述)：TODO 订单详情数据
	* @param  @return
	* @param  @throws 
	* @return PageData
	* @author Adger
	* @date 2018年6月25日上午10:23:45
	*
	 */
	public PageData gdDetailPd(PageData order, PageData userPd, PageData productPd, PageData schemePd, PageData pd) throws ParseException {
		PageData gdDetailPd = new PageData();
		
		String startDate = pd.getString("GD_START_DATE");//起保日期
		int date = Integer.parseInt(schemePd.get("SAFEGUARD_DEADLINE").toString());//保障期限
		String unit = schemePd.getString("DEADLINE_UNIT");//期限单位
		String endDate = "";//终保日期
		if (date == 500) {
			gdDetailPd.put("GD_DEADLINE", "终身");//保障期限
		} else {
			int year = 0;
			int month = 0;
			int day = 0;
			switch (unit) {
			case "YEAR":
				year = date;
				break;
			case "MONTH":
				month = date;
				break;
			case "DAY":
				day = date;
				break;
			default:
				break;
			}
			endDate = DateUtil.getCustomDateTime(startDate, year, month, day, 0, 0, -1); //终保日期
			gdDetailPd.put("GD_DEADLINE", date + Tool.unitToChinese(unit));//保障期限
		}
		//订单详情数据
		gdDetailPd.put("GDID", order.getString("detailId"));//订单详情ID
		gdDetailPd.put("ORDER_ID", order.getString("orderId"));//订单ID
		gdDetailPd.put("ORDER_NO", order.getString("orderNo"));//订单编号
		gdDetailPd.put("APPLI_ID", order.getString("appliId"));//投保人ID
		gdDetailPd.put("APPLI_ID_NUMBER", pd.getString("appli_id_numner"));//投保人证件号
		gdDetailPd.put("RECOG_ID", order.getString("recogId"));//被保人ID
		gdDetailPd.put("RECOG_ID_NUMBER", pd.getString("recog_id_numner"));//被保人证件号
		gdDetailPd.put("GD_START_DATE", startDate);//起保日期
		gdDetailPd.put("GD_ENDING_DATE", endDate);//终保日期
		gdDetailPd.put("GD_SUM_AMOUNT", schemePd.getString("SUM_COVERAGE"));//总保额
		gdDetailPd.put("SHARE_COVERAGE", pd.get("SHARE_COVERAGE"));//是否共用保额
		gdDetailPd.put("FAVOREE_TYPE", pd.get("FAVOREE_TYPE"));//受益人类型
		
		
		return gdDetailPd;
	}
	
	/**
	 * 投保人数据
	*MethodsName(方法名)：appliPd
	*Description(描述)：TODO 投保人数据
	* @param  @return
	* @param  @throws 
	* @return PageData
	* @author Adger
	* @date 2018年6月25日上午10:24:07
	*
	 */
	public PageData appliPd(PageData order, PageData pd) throws Exception {
		PageData appliPd = new PageData();
		String appli_id_numner = pd.getString("appli_id_numner");
		IDCard appliInfo = new IDCard(appli_id_numner);//根据身份证获取用户的年龄、性别、生日
		//投保人信息
		appliPd.put("ID", order.getString("appliId"));//投保人ID
		appliPd.put("APPLI_NAME", pd.getString("appli_name"));//投保人姓名
		appliPd.put("APPLI_ID_TYPE", pd.getString("appli_id_type"));//投保人证件类型
		appliPd.put("APPLI_ID_NO", appli_id_numner);//投保人证件号码
		appliPd.put("APPLI_MRK", "1");//投保人性质--1：个人、2：法人、3：企业
		appliPd.put("APPLI_SEX", appliInfo.getSex());//投保人性别
		appliPd.put("APPLI_AGE", appliInfo.getAge() + "");//投保人年龄
		appliPd.put("APPLI_BIRTHDAY", DateUtil.sdfDate.format(appliInfo.getBirthday()));//投保人生日
		appliPd.put("APPLI_MOBILE", pd.getString("appli_phone"));//投保人手机号
		appliPd.put("SEND_SMS", pd.get("EI_SUCCEED_SMS"));//是否发送短信
		appliPd.put("APPLI_EMAIL", pd.getString("appli_email"));//投保人邮箱
		appliPd.put("APPLI_ADDRESS", pd.get("appli_address"));//投保人地址
		appliPd.put("APPLI_PR_ADDRESS", pd.get("appli_pr_address"));//投保人常住城市
		appliPd.put("APPLI_IS_COMMON", pd.get("appli_is_common"));//是否设置为常用投保人
		appliPd.put("LINKER_NAME", pd.get("linker_name"));//联系人姓名
		appliPd.put("LINKER_PHONE", pd.get("linker_phone"));//联系人手机号
		appliPd.put("APPLI_POSTCODE",pd.get("appli_postcode"));//邮编
		appliPd.put("USER_ID", order.getString("userId"));//所属用户
		appliPd.put("STATE", "1");//投保人状态
		
		return appliPd;
	}
	
	/**
	 * 被保人数据
	*MethodsName(方法名)：recogPd
	*Description(描述)：TODO 被保人数据
	* @param  @return
	* @param  @throws 
	* @return PageData
	* @author Adger
	* @date 2018年6月25日上午10:24:23
	*
	 */
	public PageData recogPd(PageData order, PageData pd) throws Exception {
		PageData recogPd = new PageData();
		String recog_id_numner = pd.getString("recog_id_numner");
		IDCard recogInfo = new IDCard(recog_id_numner);//根据身份证获取用户的年龄、性别、生日
		
		//被保人信息
		recogPd.put("ID", order.getString("recogId"));//被保人ID
		recogPd.put("RECOG_NAME", pd.getString("recog_name"));//被保人姓名
		recogPd.put("RECOG_ID_TYPE", pd.getString("recog_id_type"));//被保人证件类型
		recogPd.put("RECOG_ID_NO", recog_id_numner);//被保人证件号码
		recogPd.put("RECOG_SEX", recogInfo.getSex());//被保人性别
		recogPd.put("RECOG_AGE", recogInfo.getAge() + "");//被保人年龄
		recogPd.put("RECOG_BIRTHDAY", DateUtil.sdfDate.format(recogInfo.getBirthday()));//被保人生日
		recogPd.put("RECOG_MRK", "1");//被保人性质--1：个人、2：法人、3：企业
		recogPd.put("RECOG_JINPO", pd.get("JINPO"));//被保人社保
		recogPd.put("RECOG_MOBILE", pd.get("recog_phone"));//被保人手机
		recogPd.put("RECOG_EMAIL", pd.get("recog_email"));//被保人邮箱
		recogPd.put("RECOG_ADDRESS", pd.get("recog_address"));//被保人地址
		recogPd.put("RECOG_PR_ADDRESS", pd.get("recog_pr_address"));//被保人常住城市
		recogPd.put("RECOG_IS_COMMON", pd.get("recog_is_common"));//是否设置为常用投保人
		recogPd.put("GUARANTEE_TARGET", pd.get("guarantee_target"));//保障对象（与投保人关系）
		recogPd.put("USER_ID", order.getString("userId"));//所属用户
		recogPd.put("STATE", "1");//被保人状态
		
		return recogPd;
	}
	
	/**
	 * 受益人数据
	*MethodsName(方法名)：被保人数据
	*Description(描述)：TODO 受益人数据
	* @param  @return
	* @param  @throws 
	* @return PageData
	* @author Adger
	* @date 2018年6月25日上午10:24:38
	*
	 */
	public PageData favoreePd(PageData order, PageData pd) {
		PageData favoreePd = new PageData();
		
		favoreePd.put("ID", order.getString("favoreeId"));
		favoreePd.put("ORDER_ID", order.getString("orderId"));
		favoreePd.put("FAVOREE_TYPE", pd.get("FAVOREE_TYPE"));
		favoreePd.put("FAVOREE_GRADE", pd.get("FAVOREE_GRADE"));
		favoreePd.put("FAVOREE_RELATION", pd.get("FAVOREE_RELATION"));
		favoreePd.put("FAVOREE_NAME", pd.get("FAVOREE_NAME"));
		favoreePd.put("CREDENTIALS_TYPE", pd.get("CREDENTIALS_TYPE"));
		favoreePd.put("ID_NUMBER", pd.get("ID_NUMBER"));
		favoreePd.put("FRONT_SCAN", pd.get("FRONT_SCAN"));
		favoreePd.put("VERSO_SCAN", pd.get("VERSO_SCAN"));
		favoreePd.put("BENEFIT_RATIO", pd.get("BENEFIT_RATIO"));
		favoreePd.put("USER_ID", order.getString("userId"));
		
		return favoreePd;
	}
}
