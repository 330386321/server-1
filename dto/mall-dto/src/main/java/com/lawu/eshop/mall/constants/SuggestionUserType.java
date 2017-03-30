package com.lawu.eshop.mall.constants;

/**
 * @author Sunny
 * @date 2017/3/29
 */
public enum SuggestionUserType {
	/**
	 * 商家
	 */
	MEMBER((byte)1),
	/**
	 * 会员
	 */
	MERCHANT((byte)2);
	
	SuggestionUserType (Byte value) {
		this.value = value;
	}
	
	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}
	
}
