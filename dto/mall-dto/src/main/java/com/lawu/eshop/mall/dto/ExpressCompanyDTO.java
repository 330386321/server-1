package com.lawu.eshop.mall.dto;

/**
 * 意见反馈BO
 *
 * @author Sunny
 * @date 2017/3/24
 */
public class ExpressCompanyDTO {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 网址
	 */
	private String homepage;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 排序
	 */
	private Integer ordinal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}
	
}
