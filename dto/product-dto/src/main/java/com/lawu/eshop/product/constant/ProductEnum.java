package com.lawu.eshop.product.constant;

/**
 * 
 * <p>
 * Description: 商品类枚举
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午7:27:24
 *
 */
public enum ProductEnum {
	
	PRODUCT_STATUS_DEL((byte)0x01),		//已删除
	PRODUCT_STATUS_UP((byte)0x02),		//已上架
	PRODUCT_STATUS_DOWN((byte)0x03);	//已下架
	
	public Byte val;
	ProductEnum(Byte val){
		this.val = val;
	}
	
	public static ProductEnum getEnum(Byte val){
		ProductEnum[] values = ProductEnum.values();
		for (ProductEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
	
	
	
}
