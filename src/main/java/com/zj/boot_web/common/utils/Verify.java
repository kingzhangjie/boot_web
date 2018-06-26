package com.zj.boot_web.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.base.PublicResultConstant;

public class Verify {


	/**
	 * 订单参数校检
	*MethodsName(方法名)：orderParamChecksum
	*Description(描述)：TODO 订单参数校检
	* @param  @return
	* @param  @throws 
	* @return boolean
	* @author Adger
	* @date 2018年6月24日下午3:49:21
	*
	 */
	public static boolean orderParamChecksum(PageData data) {
		boolean isCheck = true;
		if (ComUtil.isEmpty(data)) { // 无请求的参数
			isCheck = false;
			PublicResultConstant.PARAM_ERROR.setMsg("参数为空，未检查到请求参数数据");
			return isCheck;
		} else {
			if (ComUtil.isEmpty(data.get("appli_name")) || data.getString("appli_name").length() < 2) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("投保人姓名格式校检有误[姓名不能为空且最少两位字符]");
				return isCheck;
			}
			if (ComUtil.isEmpty(data.get("appli_phone")) || !data.getString("appli_name").matches("^1[3|4|5|7|8][0-9]\\d{8}$")) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("投保人手机号码格式校检有误[手机号码不能为空且为正常11位手机号码]");
				return isCheck;
			}
			if (ComUtil.isEmpty(data.get("appli_id_numner")) || !Tool.isValidate18Idcard(data.getString("appli_id_numner"))) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("投保人身份证号码格式校检有误[身份证号码不能为空且为正常18位身份证号码]");
				return isCheck;
			}
			if (ComUtil.isEmpty(data.get("appli_email")) || !data.getString("appli_email").matches("^(.+)@(.+)$")) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("投保人电子邮箱格式校检有误[电子邮箱不能为空且为正常可使用邮箱]");
				return isCheck;
			}
			if (ComUtil.isEmpty(data.get("recog_name")) || data.getString("recog_name").length() < 2) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("被保人姓名格式校检有误[姓名不能为空且最少两位字符]");
				return isCheck;
			}
			if (ComUtil.isEmpty(data.get("recog_id_numner")) || !Tool.isValidate18Idcard(data.getString("recog_id_numner"))) {
				isCheck = false;
				PublicResultConstant.PARAM_ERROR.setMsg("被保人身份证号码格式校检有误[身份证号码不能为空且为正常18位身份证号码]");
				return isCheck;
			}
		}
		return isCheck;
	}
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }
}
