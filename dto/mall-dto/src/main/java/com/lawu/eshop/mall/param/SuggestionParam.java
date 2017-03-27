package com.lawu.eshop.mall.param;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;

public class SuggestionParam implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	@ApiParam(name = "userNum", required = true, value = "用户编号")
	private String userNum;
	
	/**
	 * 建议内容
	 */
	@ApiParam(name = "content", required = true, value = "建议内容")
	private String content;
	
	/**
	 * 用户类型，1是商家，2是会员
	 */
	@ApiParam(name = "userType", required = true, value = "用户类型，1是商家，2是会员")
	private Byte userType;
	
	/**
	 * 客户端类型，1是android，2是ios
	 */
	@ApiParam(name = "clientType", required = true, value = "客户端类型，1是android，2是ios")
	private Byte clientType;
	
	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Byte getClientType() {
		return clientType;
	}

	public void setClientType(Byte clientType) {
		this.clientType = clientType;
	}

}
