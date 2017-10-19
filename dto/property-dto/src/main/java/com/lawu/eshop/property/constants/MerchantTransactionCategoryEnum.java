package com.lawu.eshop.property.constants;

/**
 * 商家余额交易明细大类
 */
public enum MerchantTransactionCategoryEnum {

	ALL((byte) 0x01, "全部分类"),
	RECHARGE((byte) 0x02, "充值"),
	RECOMMEND_INCOME((byte) 0x03, "推荐E友收益"),
	WITHDRAW((byte) 0x04, "提现"),
	PAY_INCOME((byte) 0x05, "买单收入"),
	PRODUCT_INCOME((byte) 0x06, "商品收入"),
	REFUND_MONEY((byte) 0x07, "退款"),
	DUE_IN((byte) 0x08, "待收货款");

	private Byte value;
	private String name;

	MerchantTransactionCategoryEnum(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public Byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static MerchantTransactionCategoryEnum getEnum(Byte val) {
		MerchantTransactionCategoryEnum[] values = MerchantTransactionCategoryEnum.values();
		for (MerchantTransactionCategoryEnum object : values) {
			if (object.getValue().equals(val)) {
				return object;
			}
		}
		return null;
	}
}