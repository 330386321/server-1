package com.lawu.eshop.order.srv.utils.express.kuaidi100.constants;

/**
 * 快递100查询状态码
 * 
 * @author jiangxinjun
 * @date 2017年9月21日
 */
public enum StatusEnum {

	NO_INFO("0", "物流单暂无结果"),

	SEARCH_SUCCESSFUL("1", "查询成功"),

	INTERFACE_EXCEPTION("2", "接口出现异常");

	private String value;
	
	private String label;
	
	StatusEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}

	public static StatusEnum getEnum(String value) {
		for (StatusEnum item : StatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
