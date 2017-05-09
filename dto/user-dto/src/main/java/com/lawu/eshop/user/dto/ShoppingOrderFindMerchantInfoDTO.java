package com.lawu.eshop.user.dto;

import java.io.Serializable;

/**
 * @author Sunny
 * @date 2017/4/10
 */
public class ShoppingOrderFindMerchantInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商家Id
	 */
	private Long merchantId;
	
    /**
    * 商家门店id
    */
    private Long merchantStoreId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
	
	/**
	 * 商家是否无理由退货
	 */
	private Boolean isNoReasonReturn;
	
	/**
	 * 当前用户是否是商家的粉丝
	 */
	private Boolean isFans;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public Long getMerchantStoreId() {
		return merchantStoreId;
	}

	public void setMerchantStoreId(Long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public Boolean getIsFans() {
		return isFans;
	}

	public void setIsFans(Boolean isFans) {
		this.isFans = isFans;
	}

}
