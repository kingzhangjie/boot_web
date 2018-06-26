package com.zj.boot_web.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IDCard {
	// 省份
	private String province;
	// 城市
	private String city;
	// 区县
	private String region;
	// 年份
	private int year;
	// 月份
	private int month;
	// 日期
	private int day;
	// 性别
	private String gender;
	//性别
	private String sex;
	// 出生日期
	private Date birthday;
	// 年龄
	private int age;

	private Map<String, String> cityCodeMap = new HashMap<String, String>() {
		{
			this.put("11", "北京");
			this.put("12", "天津");
			this.put("13", "河北");
			this.put("14", "山西");
			this.put("15", "内蒙古");
			this.put("21", "辽宁");
			this.put("22", "吉林");
			this.put("23", "黑龙江");
			this.put("31", "上海");
			this.put("32", "江苏");
			this.put("33", "浙江");
			this.put("34", "安徽");
			this.put("35", "福建");
			this.put("36", "江西");
			this.put("37", "山东");
			this.put("41", "河南");
			this.put("42", "湖北");
			this.put("43", "湖南");
			this.put("44", "广东");
			this.put("45", "广西");
			this.put("46", "海南");
			this.put("50", "重庆");
			this.put("51", "四川");
			this.put("52", "贵州");
			this.put("53", "云南");
			this.put("54", "西藏");
			this.put("61", "陕西");
			this.put("62", "甘肃");
			this.put("63", "青海");
			this.put("64", "宁夏");
			this.put("65", "新疆");
			this.put("71", "台湾");
			this.put("81", "香港");
			this.put("82", "澳门");
			this.put("91", "国外");
		}
	};

	private Tool validator = null;

	/**
	 * 通过构造方法初始化各个成员属性
	 */
	public IDCard(String idcard) {
		try {
			validator = new Tool();
			//if (validator.isValidate18Idcard(idcard)) {
				// 获取省份
				String provinceId = idcard.substring(0, 2);
				Set<String> key = this.cityCodeMap.keySet();
				for (String id : key) {
					if (id.equals(provinceId)) {
						this.province = this.cityCodeMap.get(id);
						break;
					}
				}

				// 获取性别
				String id17 = idcard.substring(16, 17);
				if (Integer.parseInt(id17) % 2 != 0) {
					this.gender = "男";
					this.sex = "1";
				} else {
					this.gender = "女";
					this.sex = "2";
				}

				// 获取出生日期
				String birthday = idcard.substring(6, 14);
				Date birthdate = new SimpleDateFormat("yyyyMMdd")
						.parse(birthday);
				this.birthday = birthdate;
				GregorianCalendar currentDay = new GregorianCalendar();
				currentDay.setTime(birthdate);
				this.year = currentDay.get(Calendar.YEAR);
				this.month = currentDay.get(Calendar.MONTH) + 1;
				this.day = currentDay.get(Calendar.DAY_OF_MONTH);

				// 获取年龄
				/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
				String year = simpleDateFormat.format(new Date());
				this.age = Integer.parseInt(year) - this.year;*/
				this.age = getAge(birthdate);

			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	@Override
	public String toString() {
		return "IDCard [province=" + province + ", city=" + city + ", region="
				+ region + ", year=" + year + ", month=" + month + ", day="
				+ day + ", gender=" + gender + ", sex=" + sex + ", birthday="
				+ DateUtil.sdfDate.format(birthday) + ", age=" + age + "]";
	}

	/*
	 * public static void main(String[] args) { String idcard =
	 * "410225199411145817"; IdcardInfoExtractor ie = new
	 * IdcardInfoExtractor(idcard); System.out.println(ie.toString()); }
	 */

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	//由出生日期获得年龄  
    public  int getAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }  
}
