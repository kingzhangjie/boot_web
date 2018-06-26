package com.zj.boot_web.common.utils;

import java.util.Date;
import java.util.UUID;

public class Uuid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 得到32位字符串
	*MethodsName(方法名)：
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年6月11日下午2:45:39
	*
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	
	/**
	 * 生成订单编号（18位）
	 *MethodsName(方法名)：getOrderNo
	 *Description(描述)：TODO 生成订单编号（18位）
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午3:08:45
	 *
	 */
	public static String getOrderNo() {
		Date date = new Date();
		String time = DateUtil.sdfTimes.format(date);//得到当前系统时间精确到秒
		String subTime = time.substring(2);//截取掉前两位
		//生成六位随机数字
        int suiji = (int) ((Math.random()*9+1)*10000);
		String orderNo = subTime + (suiji + "");//得到18位编号
		
		return orderNo;
	}
}
