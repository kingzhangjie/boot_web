package com.zj.boot_web.common.base;


/**
 * 公共返回数据格式类
 *TypesName(类名)：PublicResult
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:09:39
 *
 */
public class PublicResult <T> extends BaseResult<T> {

    public static final String DEFAULT_CODE = "90000003";
    public PublicResult(String message, T data) {
    	super(DEFAULT_CODE, message, data);
    }
    public PublicResult(PublicResultConstant publicResultConstant, T data) {
        super(publicResultConstant.getCode(), publicResultConstant.getMsg(), data);
    }
	/*public PublicResult(PublicResultConstant publicResultConstant, List<T> data) {
		// TODO Auto-generated constructor stub
		super(publicResultConstant.getCode(), publicResultConstant.getMsg(), data);
	}*/
}
