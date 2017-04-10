package com.lawu.eshop.ad.constants;

public enum AdTypeEnum {
	
	AD_TYPE_FLAT((byte) 0x01),      //平面
	AD_TYPE_VIDEO((byte) 0x02),     //视频
	AD_TYPE_PRAISE((byte) 0x02);     //E赞
    public Byte val;

    AdTypeEnum(Byte val) {
        this.val = val;
    }

    public static AdTypeEnum getEnum(Byte val) {
    	AdTypeEnum[] values = AdTypeEnum.values();
        for (AdTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}