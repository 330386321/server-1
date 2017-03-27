package com.lawu.eshop.mall.dto;

import java.util.Date;

/**
 * 意见反馈BO
 *
 * @author Sunny
 * @date 2017/3/24
 */
public class SuggestionDTO {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户编号 
	 */
	private String userNum;
	
	/**
	 * 建议内容
	 */
	private String content;
	
	/**
	 * 客户端类型，1是android，2是ios
	 */
	private Byte userType;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
