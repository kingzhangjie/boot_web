package com.zj.boot_web.common.base;


public class BaseResult<T> {
    private String code;
    private String msg;
    private T data;
    //private List<T> datas;

    public BaseResult() {
    }

    public BaseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /*public BaseResult(String code, String msg, List<T> datas) {
    	this.code = code;
        this.msg = msg;
        this.datas = datas;
	}*/

	public String getCode() {
        return code;
    }

    public void setCode(String result) {
        this.code = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "BaseResult [code=" + code + ", msg=" + msg + ", data=" + data
				+ "]";
	}
}
