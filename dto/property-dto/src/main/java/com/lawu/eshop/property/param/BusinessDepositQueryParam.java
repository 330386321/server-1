package com.lawu.eshop.property.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 运营平台保证金查询条件参数对象
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 下午1:23:17
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessDepositQueryParam  extends AbstractPageParam{

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

	@ApiParam(name = "businessDepositStatusEnum", required = true, value = "状态")
	private BusinessDepositStatusEnum businessDepositStatusEnum;
	
	@ApiParam(name = "transactionPayTypeEnum", value = "支付方式")
	private TransactionPayTypeEnum transactionPayTypeEnum;

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

	public BusinessDepositStatusEnum getBusinessDepositStatusEnum() {
		return businessDepositStatusEnum;
	}

	public void setBusinessDepositStatusEnum(BusinessDepositStatusEnum businessDepositStatusEnum) {
		this.businessDepositStatusEnum = businessDepositStatusEnum;
	}

	public TransactionPayTypeEnum getTransactionPayTypeEnum() {
		return transactionPayTypeEnum;
	}

	public void setTransactionPayTypeEnum(TransactionPayTypeEnum transactionPayTypeEnum) {
		this.transactionPayTypeEnum = transactionPayTypeEnum;
	}

}
