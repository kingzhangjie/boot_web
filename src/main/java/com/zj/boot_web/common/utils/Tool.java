package com.zj.boot_web.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zj.boot_web.common.base.PageData;

public class Tool {

	public static void main(String[] args) {
		System.out.println(notEmpty("123"));
	}

	/**
	 * 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
	 * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
	 * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
	 * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
	 * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
	 * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	 */
	protected String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" },
			{ "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
			{ "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
			{ "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
			{ "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
			{ "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
			{ "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" },
			{ "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
			{ "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" },
			{ "91", "国外" } };

	private String cityCode[] = { "11", "12", "13", "14", "15", "21", "22",
			"23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
			"44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
			"64", "65", "71", "81", "82", "91" };

	// 每位加权因子
	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
			8, 4, 2 };

	// 第18位校检码
	private String verifyCode[] = { "1", "0", "X", "9", "8", "7", "6", "5",
			"4", "3", "2" };

	/**
	 * <p>
	 * 判断18位身份证的合法性
	 * </p>
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * <p>
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 * </p>
	 * <p>
	 * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
	 * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
	 * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
	 * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
	 * </p>
	 * <p>
	 * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2
	 * </p>
	 * <p>
	 * 2.将这17位数字和系数相乘的结果相加。
	 * </p>
	 * <p>
	 * 3.用加出来和除以11，看余数是多少？
	 * </p>
	 * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
	 * 2。
	 * <p>
	 * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 * </p>
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isValidate18Idcard(String idcard) {
		if (notEmpty(idcard) && idcard.length() == 18) {
			// 获取前17位
			String idcard17 = idcard.substring(0, 17);
			// 获取第18位
			String idcard18Code = idcard.substring(17, 18);
			char c[] = null;
			String checkCode = "";
			// 是否都为数字
			if (isDigital(idcard17)) {
				c = idcard17.toCharArray();
			} else {
				return false;
			}

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				bit = converCharToInt(c);

				int sum17 = 0;

				sum17 = getPowerSum(bit);

				// 将和值与11取模得到余数进行校验码判断
				checkCode = getCheckCodeBySum(sum17);
				if (null == checkCode) {
					return false;
				}
				// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
				if (!idcard18Code.equalsIgnoreCase(checkCode)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将15位的身份证转成18位身份证
	 * 
	 * @param idcard
	 * @return
	 */
	public String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		// 非15位身份证
		if (idcard.length() != 15) {
			return null;
		}

		if (isDigital(idcard)) {
			// 获取出生年月日
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				// 将字符数组转为整型数组
				bit = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				// 获取和值与11取模得到余数进行校验码
				checkCode = getCheckCodeBySum(sum17);
				// 获取不到校验位
				if (null == checkCode) {
					return null;
				}

				// 将前17位与第18位校验码拼接
				idcard17 += checkCode;
			}
		} else { // 身份证包含数字
			return null;
		}
		return idcard17;
	}

	/**
	 * 15位和18位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard);
	}

	/**
	 * 15位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean is15Idcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
				idcard);
	}

	/**
	 * 18位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean is18Idcard(String idcard) {
		return Pattern
				.matches(
						"^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
						idcard);
	}

	/**
	 * 数字验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param bit
	 * @return
	 */
	public static int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	/**
	 * 将和值与11取模得到余数进行校验码判断
	 * 
	 * @param checkCode
	 * @param sum17
	 * @return 校验位
	 */
	public static String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	/**
	 * 将字符数组转为整型数组
	 * 
	 * @param c
	 * @return
	 * @throws NumberFormatException
	 */
	public static int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	/**
	 * 得到六位随机数 MethodsName(方法名)：getRandomNum6 Description(描述)：TODO 得到六位随机数
	 * 
	 * @param @return
	 * @return int
	 * @author Adger
	 * @date 2017年11月27日 下午4:18:21
	 */
	public static int getRandomNum6() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;
	}

	/**
	 * 验证字符串非空 MethodsName(方法名)：notEmpty Description(描述)：TODO 验证字符串非空
	 * 
	 * @param @param s
	 * @param @return
	 * @return boolean
	 * @author Adger
	 * @date 2017年11月27日 下午4:19:13
	 */
	public static boolean notEmpty(String s) {
		return (s != null) && (!"".equals(s)) && (!"null".equals(s));
	}

	/**
	 * 验证字符串为空 MethodsName(方法名)：isEmpty Description(描述)：TODO 验证字符串为空
	 * 
	 * @param @param s
	 * @param @return
	 * @return boolean
	 * @author Adger
	 * @date 2017年11月27日 下午4:20:09
	 */
	public static boolean isEmpty(String s) {
		return (s == null) || ("".equals(s)) || ("null".equals(s));
	}

	/**
	 * 分割字符串为String[]数组 MethodsName(方法名)： Description(描述)：TODO
	 * 
	 * @param @param str 要分割的字符串
	 * @param @param splitRegex 分割字符
	 * @param @return
	 * @return String[]
	 * @author Adger
	 * @date 2017年11月27日 下午4:20:55
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 写入文件 MethodsName(方法名)： Description(描述)：TODO
	 * 
	 * @param @param fileP
	 * @param @param content
	 * @return void
	 * @author Adger
	 * @date 2017年11月27日 下午4:14:51
	 */
	public static void writeFile(String fileP, String content) {
		String filePath = String.valueOf(Thread.currentThread()
				.getContextClassLoader().getResource(""))
				+ "../../";
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if (filePath.indexOf(":") != 1)
			filePath = File.separator + filePath;
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(filePath), "utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证电子邮箱合法性 MethodsName(方法名)：checkEmail Description(描述)：TODO 验证电子邮箱合法性
	 * 
	 * @param @param email
	 * @param @return
	 * @return boolean
	 * @author Adger
	 * @date 2017年11月27日 下午4:14:17
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号 MethodsName(方法名)：checkMobileNumber Description(描述)：TODO 验证手机号
	 * 
	 * @param @param mobileNumber
	 * @param @return
	 * @return boolean
	 * @author Adger
	 * @date 2017年11月27日 下午4:13:14
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean checkKey(String paraname, String FKEY) {
		paraname = paraname == null ? "" : paraname;
		return MD5.md5(paraname + DateUtil.getYyyyMMdd() + ",fh,").equals(FKEY);
	}

	/**
	 * 读取TXT文件 MethodsName(方法名)：readTxtFile Description(描述)：TODO 读取TXT文件
	 * 
	 * @param @param fileP
	 * @param @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月27日 下午4:13:54
	 */
	@SuppressWarnings("resource")
	public static String readTxtFile(String fileP) {
		try {
			String filePath = String.valueOf(Thread.currentThread()
					.getContextClassLoader().getResource(""))
					+ "../../";
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if (filePath.indexOf(":") != 1) {
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if ((file.isFile()) && (file.exists())) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				if ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			} else {
				// System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
			}
		} catch (Exception e) {
			// System.out.println("读取文件内容出错");
		}
		return "";
	}

	/**
	 * 根据订单的状态码转为汉字显示 MethodsName(方法名)：stateToFont Description(描述)：TODO
	 * 根据订单的状态码转为汉字显示
	 * 
	 * @param @param state
	 * @param @param type
	 * @param @return
	 * @return String
	 * @author Adger
	 * @date 2017年12月19日 下午4:56:56
	 */
	public static String stateToFont(String state, String type) {
		String font = "";
		if (type == "pay" || "pay".equals(type)) {
			switch (state) {
			case "00":
				font = "支付失败";
				break;
			case "01":
				font = "支付成功";
				break;
			case "02":
				font = "待支付";
				break;
			case "03":
				font = "支付取消";
				break;
			default:
				font = "待支付";
				break;
			}
		} else {
			switch (state) {
			case "00":
				font = "出单中";
				break;
			case "01":
				font = "出单中";
				break;
			case "02":
				font = "出单中";
				break;
			case "03":
				font = "已出单";
				break;
			case "04":
				font = "待打印";
				break;
			case "05":
				font = "已打印";
				break;
			case "06":
				font = "修改中";
				break;
			case "07":
				font = "退款中";
				break;
			case "08":
				font = "已退款";
				break;
			case "09":
				font = "已到期";
				break;
			case "10":
				font = "已退保";
				break;
			case "11":
				font = "冻结";
				break;
			case "90":
				font = "待修改";
				break;
			case "91":
				font = "暂存";
				break;
			case "92":
				font = "失效";
				break;
			case "99":
				font = "测试订单";
				break;
			default:
				font = "出单中";
				break;
			}
		}
		return font;
	}
	
	public static String unitToChinese(String unit) {
		String chinese = "";
		
		switch (unit) {
		case "YEAR":
			chinese = "年";
			break;
		case "MONTH":
			chinese = "月";
			break;
		case "DAY":
			chinese = "日";
			break;
		case "HOUR":
			chinese = "时";
			break;
		case "MINUTE":
			chinese = "分";
			break;
		case "SECOND":
			chinese = "秒";
			break;
		default:
			chinese = "";
			break;
		}
		
		return chinese;
	}

	/**
	 * 通用分页页码计算工具 MethodsName(方法名)：getPageAndSize Description(描述)：TODO
	 * 通用分页页码计算工具
	 * 
	 * @param @return
	 * @param @throws
	 * @return PageData
	 * @author Adger
	 * @date 2018年6月24日下午3:46:11
	 */
	public static PageData getPageAndSize(PageData pd) {

		int page = 1;
		if (!ComUtil.isEmpty(pd.get("page"))) {
			page = Integer.parseInt(pd.get("page").toString());
		}
		int size = 10;
		if (!ComUtil.isEmpty(pd.get("limit"))) {
			size = Integer.parseInt(pd.get("limit").toString());
		}
		if (page < 1) {
			page = 1;
		}
		if (size < 10) {
			size = 10;
		}
		page = (page - 1) * size;
		pd.put("page", page);
		pd.put("size", size);
		return pd;
	}
}
