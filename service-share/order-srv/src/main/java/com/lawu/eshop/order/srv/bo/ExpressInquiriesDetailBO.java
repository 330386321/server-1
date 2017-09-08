package com.lawu.eshop.order.srv.bo;

import java.util.List;

import com.lawu.eshop.order.srv.utils.express.kdniao.constants.StateEnum;

/**
 * 物流实时查询对外暴露数据
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class ExpressInquiriesDetailBO {
	
	/**
	 * 快递公司编码
	 */
	private String shipperCode;
	
	/**
	 * 物流运单号
	 */
	private String logisticCode;
	
	/**
	 * 成功与否
	 */
	private Boolean success;
	
	/**
	 * 失败原因
	 */
	private String reason;
	
	/**
	 * 物流状态
	 */
	private StateEnum state;
	
	/**
	 * 物流轨迹
	 */
	private List<TraceBO> traces;

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	public List<TraceBO> getTraces() {
		return traces;
	}

	public void setTraces(List<TraceBO> traces) {
		this.traces = traces;
	}
	
}
