package com.lawu.eshop.property.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;

import io.swagger.annotations.ApiParam;

public class CashBackageQueryParam  extends AbstractPageParam{

	@ApiParam(name = "content", value = "搜索文本")
	private String content;

	@ApiParam(name = "regionPath", value = "区域路径(格式：省ID/市ID/区ID)")
	private String regionPath;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiParam(name = "beginDate", required = true, value = "开始时间")
	private Date beginDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiParam(name = "endDate", required = true, value = "结束时间")
	private Date endDate;

	@ApiParam(name = "cashStatsuEnum", required = true, value = "提现状态(全部传空字符串)")
	private CashStatusEnum cashStatsuEnum;
	
	@ApiParam(name = "userTypeEnum", required = true, value = "用户类型")
	private UserTypeEnum userTypeEnum;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegionPath() {
		return regionPath;
	}

	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CashStatusEnum getCashStatsuEnum() {
		return cashStatsuEnum;
	}

	public void setCashStatsuEnum(CashStatusEnum cashStatsuEnum) {
		this.cashStatsuEnum = cashStatsuEnum;
	}

	public UserTypeEnum getUserTypeEnum() {
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}

}
