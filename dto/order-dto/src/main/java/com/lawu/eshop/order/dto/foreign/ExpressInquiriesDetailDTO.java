package com.lawu.eshop.order.dto.foreign;

import java.util.List;

import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物流实时查询对外暴露数据
 * 
 * @author Sunny
 * @date 2017/4/10
 */
@ApiModel
public class ExpressInquiriesDetailDTO {
	
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
	 */
	@ApiModelProperty(value = "物流状态(ON_THE_WAY-在途中|SIGN_IN-签收|PROBLEM_PIECES-问题件)", required = true)
	private ExpressInquiriesDetailStateEnum state;
	
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

	public ExpressInquiriesDetailStateEnum getState() {
		return state;
	}

	public void setState(ExpressInquiriesDetailStateEnum state) {
		this.state = state;
	}

	public List<TraceDTO> getTraces() {
		return traces;
	}

	public void setTraces(List<TraceDTO> traces) {
		this.traces = traces;
	}
	
}
