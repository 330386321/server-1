package com.lawu.eshop.order.constants;

/**
 * 快递数据提供者枚举
 * 
 * @author jiangxinjun
 * @date 2017年9月22日
 */
public enum ExpressProviderTypeEnum {

	KUAIDI100("快递100"),

	KUAIDINIAO("快递鸟");

	private String label;

	ExpressProviderTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
