package com.lawu.eshop.order.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询物流轨迹srv暴露给api参数
 * 
 * @author jiangxinjun
 * @date 2017年9月5日
 */
public class ExpressQueryParam {

	/**
	 * 快递单号
	 */
	@NotBlank(message = "快递单号不能为空")
	private String expNo;
	
	/**
	 * 快递鸟快递公司编号
	 */
	private String expCode;
	
	/**
	 * 快递100快递公司编号
	 */
	private String kuaidi100ExpCode;

	public String getExpNo() {
		return expNo;
	}

	public void setExpNo(String expNo) {
		this.expNo = expNo;
	}

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public String getKuaidi100ExpCode() {
		return kuaidi100ExpCode;
	}

	public void setKuaidi100ExpCode(String kuaidi100ExpCode) {
		this.kuaidi100ExpCode = kuaidi100ExpCode;
	}
	
}
