package com.lawu.eshop.ad.constants;

public enum AdPraiseStatusEnum {
	
	AD_STATUS_SHOOT((byte) 0x01),      //开枪中
	AD_STATUS_TOBEGIN((byte) 0x02),     //即将开始
	AD_STATUS_END((byte) 0x03);     //已结束
    public Byte val;
    

    AdPraiseStatusEnum(Byte val) {
        this.val = val;
    }

    public static AdPraiseStatusEnum getEnum(Byte val) {
    	AdPraiseStatusEnum[] values = AdPraiseStatusEnum.values();
        for (AdPraiseStatusEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
