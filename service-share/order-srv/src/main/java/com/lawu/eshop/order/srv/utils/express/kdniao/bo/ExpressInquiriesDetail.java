package com.lawu.eshop.order.srv.utils.express.kdniao.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 快递鸟实时查询封装数据
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class ExpressInquiriesDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * 用户ID
	 */
	private String EBusinessID;
	
	/**
	 * 订单编号
	 */
	private String OrderCode;
	
	/**
	 * 快递公司编码
	 */
	private String ShipperCode;
	
	/**
	 * 成功与否
	 */
	private Boolean Success;
	
	/**
	 * 失败原因
	 */
	private String Reason;
	
	/**
	 * 物流状态
	 * 
	 * 0-此单无物流信息,2-在途中,3-签收,4-问题件
	 */
	private String State;
	
	/**
	 * 物流轨迹
	 */
	private List<Trace> Traces;

	public String getEBusinessID() {
		return EBusinessID;
	}

	public void setEBusinessID(String eBusinessID) {
		EBusinessID = eBusinessID;
	}

	public String getOrderCode() {
		return OrderCode;
	}

	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}

	public String getShipperCode() {
		return ShipperCode;
	}

	public void setShipperCode(String shipperCode) {
		ShipperCode = shipperCode;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public List<Trace> getTraces() {
		return Traces;
	}

	public void setTraces(List<Trace> traces) {
		Traces = traces;
	}
	
}
