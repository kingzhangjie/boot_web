package com.zj.boot_web.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *TypesName(类名)：StringUtil
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:26:06
 *
 */
public class StringUtil {

    /*public static String chineseToSpelling(String chinese) throws Exception {
        String pinyin = "";
        HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();
        pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] pinyinArray = null;
        for(char ch : chinese.toCharArray()){
            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch,pinyinOutputFormat);
            pinyin += ComUtil.isEmpty(pinyinArray) ? ch : pinyinArray[0];
        }
        return pinyin;
    }*/

    /**
     * 获取方法中指定注解的value值返回
     * @param method 方法名
     * @param validationParamValue 注解的类名
     * @return
     */
    public static String getMethodAnnotationOne(Method method, String validationParamValue) {
        String retParam =null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                String str = parameterAnnotations[i][j].toString();
                if(str.indexOf(validationParamValue) >0){
                    retParam = str.substring(str.indexOf("=")+1,str.indexOf(")"));
                }
            }
        }
        return retParam;
    }

    /**
     * 验证url地址格式
    *MethodsName(方法名)：
    *Description(描述)：TODO 
    * @param  @return
    * @param  @throws 
    * @return boolean
    * @author Adger
    * @date 2018年6月11日下午2:30:23
    *
     */
    public static boolean isValidURLAddress(String url) {
        String pattern = "([h]|[H])([t]|[T])([t]|[T])([p]|[P])([s]|[S]){0,1}://([^:/]+)(:([0-9]+))?(/\\S*)*";
        return url.matches(pattern);
    }

    /**
     * 将utf-8编码的汉字转为中文
    *MethodsName(方法名)：utf8Decoding
    *Description(描述)：TODO 
    * @param  @return
    * @param  @throws 
    * @return String
    * @author Adger
    * @date 2018年6月11日下午2:30:09
    *
     */
    public static String utf8Decoding(String str){
        String result = str;
        try
        {
            result = URLDecoder.decode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验证电子邮箱格式
    *MethodsName(方法名)：checkEmail
    *Description(描述)：TODO 
    * @param  @return
    * @param  @throws 
    * @return boolean
    * @author Adger
    * @date 2018年6月11日下午2:30:02
    *
     */
    public static boolean checkEmail(String email) {
        if (ComUtil.isEmpty(email)) {
            return false;
        }
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
     * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            // Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
