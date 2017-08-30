package com.lawu.eshop.order.param;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;

/**
 * 
 * <p>
 * Description: 粉丝数据-粉丝增长
 * </p>
 * 
 * @author Yangqh
 * @date 2017年5月2日 下午2:24:04
 *
 */
@ApiModel
public class ReportDataParam extends ReportParam {

	@NotNull(message = "merchantId不能为空")
	private Long merchantId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date test;
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Date getTest() {
		return test;
	}

	public void setTest(Date test) {
		this.test = test;
	}
	
}
