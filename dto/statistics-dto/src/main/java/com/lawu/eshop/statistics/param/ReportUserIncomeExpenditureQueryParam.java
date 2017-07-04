package com.lawu.eshop.statistics.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.statistics.constants.UserTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户收支查询参数
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
public class ReportUserIncomeExpenditureQueryParam extends AbstractPageParam {
	
	/**
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型", required = true)
	private UserTypeEnum userType;
	
	/**
	 * 用户账号
	 */
	@ApiModelProperty(value = "用户账号", required = true)
	private String account;
	
	/**
	 * 开始日期(yyyy-MM-dd)
	 */
	@ApiModelProperty(value = "开始日期(yyyy-MM-dd)", required = true)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start;
	
	/**
	 * 结束日期(yyyy-MM-dd)
	 */
	@ApiModelProperty(value = "结束日期(yyyy-MM-dd)", required = true)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date end;

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
}
