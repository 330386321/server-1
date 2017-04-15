package com.lawu.eshop.property.param;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 商家申请退保证金
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 下午4:40:13
 *
 */
public class BusinessRefundDepositParam {

	@NotBlank(message="id不能为空")
	@ApiParam(name = "id", required = true, value = "保证金ID")
	private String id;

	@NotBlank(message="businessBankAccountId不能为空")
	@ApiParam(name = "businessBankAccountId", required = true, value = "商家银行卡ID")
	private String businessBankAccountId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessBankAccountId() {
		return businessBankAccountId;
	}

	public void setBusinessBankAccountId(String businessBankAccountId) {
		this.businessBankAccountId = businessBankAccountId;
	}


}
