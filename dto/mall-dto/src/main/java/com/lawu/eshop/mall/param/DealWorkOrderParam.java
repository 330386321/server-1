package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.WorkOrderStatusEnum;

public class DealWorkOrderParam {

	private Long id;
	
	private WorkOrderStatusEnum workOrderStatusEnum;
	
	private String replyContent;

	private Integer auditorId;
	
	private String auditorName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public WorkOrderStatusEnum getWorkOrderStatusEnum() {
		return workOrderStatusEnum;
	}

	public void setWorkOrderStatusEnum(WorkOrderStatusEnum workOrderStatusEnum) {
		this.workOrderStatusEnum = workOrderStatusEnum;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	
}
