package com.lawu.eshop.mall.param;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.mall.constants.WorkOrderTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class WorkOrderParam {
	
	@ApiModelProperty(name = "account", required = true, value = "账号")
	@NotBlank(message = "账号不能为空")
	private String account;
	
	@ApiModelProperty(name = "userNum", required = true, value = "编号")
	@NotBlank(message = "编号不能为空")
	private String userNum;
	
	@ApiModelProperty(name = "workOrderTypeEnum", required = true, value = "工单类型：MEMBER--用户提交的，MERCHANT--商家提交的")
	@NotBlank(message = "工单类型不能为空")
	private WorkOrderTypeEnum workOrderTypeEnum;
	
	@ApiModelProperty(name = "name", required = true, value = "名字：用户昵称|商家门店名称")
	@NotBlank(message = "名字不能为空")
	private String name;
	
	@ApiModelProperty(name = "content", required = true, value = "工单内容")
	@NotBlank(message = "工单内容不能为空")
	private String content;

	public String getAccount() {
		return account;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public void setAccount(String account) {
		this.account = account;
	}


	public WorkOrderTypeEnum getWorkOrderTypeEnum() {
		return workOrderTypeEnum;
	}

	public void setWorkOrderTypeEnum(WorkOrderTypeEnum workOrderTypeEnum) {
		this.workOrderTypeEnum = workOrderTypeEnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
