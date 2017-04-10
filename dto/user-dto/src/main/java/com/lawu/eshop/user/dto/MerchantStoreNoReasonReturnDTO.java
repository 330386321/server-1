package com.lawu.eshop.user.dto;

import java.io.Serializable;

/**
 * 门店是否七天无理由退货
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class MerchantStoreNoReasonReturnDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商家Id
	 */
	private Long merchantId;

	/**
	 * 商家是否无理由退货
	 */
	private Boolean isNoReasonReturn;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

}
