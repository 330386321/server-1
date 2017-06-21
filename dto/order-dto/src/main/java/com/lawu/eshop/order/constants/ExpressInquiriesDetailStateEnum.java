package com.lawu.eshop.order.constants;

/**
 * 物流状态码
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
public enum ExpressInquiriesDetailStateEnum {

	// 物流状态 0-此单无物流信息,2-在途中,3-签收,4-问题件

	/**
	 * 0-此单无物流信息
	 */
	NO_INFO("0"),

	/**
	 * 2-在途中
	 */
	ON_THE_WAY("2"),

	/**
	 * 3-签收
	 */
	SIGN_IN("3"),

	/**
	 * 4-问题件
	 */
	PROBLEM_PIECES("4");

	private String value;

	ExpressInquiriesDetailStateEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static ExpressInquiriesDetailStateEnum getEnum(String value) {
		for (ExpressInquiriesDetailStateEnum item : ExpressInquiriesDetailStateEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
