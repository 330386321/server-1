package com.lawu.eshop.user.param;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;

/**
 * 
 * <p>
 * Description: 粉丝数据-粉丝增长
 * </p>
 * @author Yangqh
 * @date 2017年5月2日 下午2:24:04
 *
 */
@ApiModel
public class ReportFansDataParam extends  ReportFansParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "merchantId不能为空")
	private Long merchantId;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	

	
}
