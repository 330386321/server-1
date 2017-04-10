package com.lawu.eshop.mall.dto.foreign;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物流实时查询对外暴露数据
 * 
 * @author Sunny
 * @date 2017/4/10
 */
@ApiModel
public class ExpressInquiriesDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 成功与否
	 */
	@ApiModelProperty(value = "成功与否", required = true)
	private Boolean success;
	
	/**
	 * 失败原因
	 */
	@ApiModelProperty(value = "失败原因")
	private String reason;
	
	/**
	 * 物流状态
	 * 
	 * 2-在途中,3-签收,4-问题件
	 */
	@ApiModelProperty(value = "物流状态(2-在途中,3-签收,4-问题件)", required = true)
	private String state;
	
	/**
	 * 物流轨迹
	 */
	@ApiModelProperty(value = "物流轨迹", required = true)
	private List<TraceDTO> traces;

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

	public List<TraceDTO> getTraces() {
		return traces;
	}

	public void setTraces(List<TraceDTO> traces) {
		this.traces = traces;
	}
	
}
