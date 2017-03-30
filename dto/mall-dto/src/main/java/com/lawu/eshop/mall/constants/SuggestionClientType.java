package com.lawu.eshop.mall.constants;

/**
 * @author Sunny
 * @date 2017/3/29
 */
public enum SuggestionClientType {
	ANDROID((byte)1),
	IOS((byte)2);
	
	SuggestionClientType (Byte value) {
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
