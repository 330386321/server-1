package com.lawu.eshop.mall.query;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.mall.constants.WorkOrderStatusEnum;
import com.lawu.eshop.mall.constants.WorkOrderTypeEnum;

public class WorkOrderQuery extends AbstractPageParam {

	private WorkOrderTypeEnum workOrderTypeEnum;
	
	private WorkOrderStatusEnum workOrderStatusEnum;
	
	private String searchContent;
	
	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public WorkOrderTypeEnum getWorkOrderTypeEnum() {
		return workOrderTypeEnum;
	}

	public void setWorkOrderTypeEnum(WorkOrderTypeEnum workOrderTypeEnum) {
		this.workOrderTypeEnum = workOrderTypeEnum;
	}

	public WorkOrderStatusEnum getWorkOrderStatusEnum() {
		return workOrderStatusEnum;
	}

	public void setWorkOrderStatusEnum(WorkOrderStatusEnum workOrderStatusEnum) {
		this.workOrderStatusEnum = workOrderStatusEnum;
	}

}
