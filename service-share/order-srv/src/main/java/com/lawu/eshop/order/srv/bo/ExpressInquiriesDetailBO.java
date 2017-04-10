package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 物流实时查询对外暴露数据
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class ExpressInquiriesDetailBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	 * 
	 * 2-在途中,3-签收,4-问题件
	 */
	private String state;
	
	/**
	 * 物流轨迹
	 */
	private List<TraceBO> traces;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<TraceBO> getTraces() {
		return traces;
	}

	public void setTraces(List<TraceBO> traces) {
		this.traces = traces;
	}
	
}
