package com.lawu.eshop.ad.constants;

public enum PositionEnum {
	
	    POSITON_RECOMMEND((byte) 0x01),      //人气推荐
	    POSITON_SHOP_TOP((byte) 0x02),     //要购物顶部广告
	    POSITON_SHOP_CHOOSE((byte) 0x03),    //要购物今日推荐
		POSITON_SHOP_GOODS((byte) 0x04),    //要购物精品
		POSITON_AD_TOP((byte) 0x05),   //看广告顶部广告    
	    SHOPPING_BUY((byte) 0x06), // E店必够
	    SHOPPING_GOODS((byte) 0x07), // 特色好货
	    SHOPPING_BENEFIT((byte) 0x08), // 实惠单品
	    SHOPPING_HOT((byte) 0x09);  //热门商品
	    public Byte val;

	    PositionEnum(Byte val) {
	        this.val = val;
	    }

	    public static PositionEnum getEnum(Byte val) {
	    	PositionEnum[] values = PositionEnum.values();
	        for (PositionEnum object : values) {
	            if (object.val.equals(val)) {
	                return object;
	            }
	        }
	        return null;
	    }

}
