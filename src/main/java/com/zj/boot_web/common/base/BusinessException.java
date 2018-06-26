package com.zj.boot_web.common.base;
/**
 * 
 *TypesName(类名)：BusinessException
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:07:10
 *
 */
public class BusinessException extends Exception{
    /**
     * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
     */
    private static final long serialVersionUID = 3455708526465670030L;

    public BusinessException(String msg){
        super(msg);
    }
}