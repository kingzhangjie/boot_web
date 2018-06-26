package com.zj.boot_web.common.utils;

import java.security.MessageDigest;

public class Sign {

	/**
	 * 得到签名
	*MethodsName(方法名)：getSignature
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年6月11日下午2:42:00
	*
	 */
	public static String getSignature(String ticket, String nonceStr,
			long timeStamp, String url) throws Exception {

		String sKey = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + timeStamp + "&url=" + url;

		return getSignature(sKey);

	}

	/**
	 * 得到签名
	*MethodsName(方法名)：getSignature
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年6月11日下午2:43:07
	*
	 */
	public static String getSignature(String sKey) throws Exception {

		String ciphertext = null;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(sKey.toString().getBytes());
		ciphertext = byteToStr(digest);
		return ciphertext.toLowerCase();

	}

	/**
	 * 数组转字符串
	*MethodsName(方法名)：
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年6月11日下午2:42:53
	*
	 */
	private static String byteToStr(byte[] byteArray) {

		String strDigest = "";

		for (int i = 0; i < byteArray.length; i++) {

			strDigest += byteToHexStr(byteArray[i]);

		}

		return strDigest;

	}

	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);

		return s;

	}
}
