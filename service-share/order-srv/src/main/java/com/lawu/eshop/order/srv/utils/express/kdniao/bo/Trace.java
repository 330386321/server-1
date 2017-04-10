package com.lawu.eshop.order.srv.utils.express.kdniao.bo;

import java.io.Serializable;

/**
 * 快递鸟实时查询封装数据
 * 物流踪迹
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class Trace implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 时间
	 */
	private String AcceptTime;
	
	/**
	 * 描述
	 */
	private String AcceptStation;
	
	/**
	 * 备注
	 */
	private String Remark;

	public String getAcceptTime() {
		return AcceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		AcceptTime = acceptTime;
	}

	public String getAcceptStation() {
		return AcceptStation;
	}

	public void setAcceptStation(String acceptStation) {
		AcceptStation = acceptStation;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}
	
}
