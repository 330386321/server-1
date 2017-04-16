package com.lawu.eshop.pay.sdk.weixin.sdk.common;

import java.io.Serializable;

/**
 * @Description 封装 返回Json 格式数据
 * @author 杨清华
 * @date 2016年4月19日
 */
public class JsonResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2244221401956011209L;

	/**
	 * 数据
	 */
	private Object data;
	
	private Object data1;
	
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 是否成功
	 */
	private boolean success;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public Object getData1() {
		return data1;
	}

	public void setData1(Object data1) {
		this.data1 = data1;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public JsonResult() {
		super();
	}

	public JsonResult(Object data, String message, boolean success) {
		this.data = data;
		this.message = message;
		this.success = success;
	}

	public JsonResult(Object data, String message) {
		this.data = data;
		this.message = message;
		this.success = true;
	}

	public JsonResult(Object data) {
		this.data = data;
		this.success = true;
	}
}
