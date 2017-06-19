package com.lawu.eshop.product.constant;

/**
 * 商品库存流水类型
 * 
 * @author Sunny
 * @date 2017年4月13日
 */
public enum ProductModelInventoryTypeEnum {

	//更新类型(0-加库存|1-减库存|2-创建订单|3-取消订单)
	
	/**
	 * 加库存
	 */
	PLUS((byte)0x01),
	
	/**
	 * 减库存
	 */
	MINUS((byte)0x02),
	
	/**
	 * 创建订单
	 */
	CREATE_ORDER((byte)0x03),
	
	/**
	 * 取消订单
	 */
	CANCEL_ORDER((byte)0x04);
	
	ProductModelInventoryTypeEnum (Byte value){
		this.value = value;
	}
	
	private Byte value;

	public Byte getValue() {
		return value;
	}

	public ProductModelInventoryTypeEnum getEnum (Byte value) {
		
		if (value == null) {
			return null;
		}
		
		for (ProductModelInventoryTypeEnum item : ProductModelInventoryTypeEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
	
}
