package com.lawu.eshop.property.constants;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午8:20:56
 *
 */
public enum LoveTypeEnum {

	AD_CLICK((byte) 0x01, "点广告"),
	AD_COMMISSION((byte) 0x02, "广告提成");

	private Byte value;

	private String name;

	LoveTypeEnum(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}