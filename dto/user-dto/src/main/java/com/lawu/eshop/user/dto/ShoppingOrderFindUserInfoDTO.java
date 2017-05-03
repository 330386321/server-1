package com.lawu.eshop.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sunny
 * @date 2017/4/10
 */
public class ShoppingOrderFindUserInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	private String memberNum;
	
	/**
	 * 商家信息
	 */
	private List<ShoppingOrderFindMerchantInfoDTO> shoppingOrderFindMerchantInfoDTOList;

	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	public List<ShoppingOrderFindMerchantInfoDTO> getShoppingOrderFindMerchantInfoDTOList() {
		return shoppingOrderFindMerchantInfoDTOList;
	}

	public void setShoppingOrderFindMerchantInfoDTOList(List<ShoppingOrderFindMerchantInfoDTO> shoppingOrderFindMerchantInfoDTOList) {
		this.shoppingOrderFindMerchantInfoDTOList = shoppingOrderFindMerchantInfoDTOList;
	}
	
}
