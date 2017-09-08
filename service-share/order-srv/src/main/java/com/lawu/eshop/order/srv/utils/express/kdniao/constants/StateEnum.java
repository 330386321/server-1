package com.lawu.eshop.order.srv.utils.express.kdniao.constants;

/**
 * 物流状态码
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
public enum StateEnum {

	NO_INFO("0", "无轨迹"),

	ON_THE_WAY("2", "在途中"),

	SIGN_IN("3", "签收"),

	PROBLEM_PIECES("4", "问题件");

	private String value;
	
	private String label;
	
	StateEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}

	public static StateEnum getEnum(String value) {
		for (StateEnum item : StateEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
