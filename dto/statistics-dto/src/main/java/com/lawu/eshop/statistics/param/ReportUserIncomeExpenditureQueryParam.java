package com.lawu.eshop.statistics.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.statistics.constants.UserTypeEnum;

/**
 * 用户收支查询参数
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
public class ReportUserIncomeExpenditureQueryParam extends AbstractPageParam {
	
	/**
	 * 报表类型
	 */
	private UserTypeEnum userType;
	
	/**
	 * 账户账号
	 */
	private String account;
	
	/**
	 * 开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start;
	
	/**
	 * 结束日期
	 */
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
