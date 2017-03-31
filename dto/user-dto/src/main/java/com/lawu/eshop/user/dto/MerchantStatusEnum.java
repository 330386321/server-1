package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.UserStatusEnum;

/**
 *
 * Created by zhangyong on 2017/3/28.
 */
public enum  MerchantStatusEnum {

    MERCHANT_STATUS_UNCHECK((byte)0x00),
    MERCHANT_STATUS_CHECKED((byte)0x01),
    MERCHANT_STATUS_CHECK_FAILED((byte)0x02),
    MERCHANT_STATUS_NOT_MONEY((byte)0x03),
    MERCHANT_STATUS_GIVE_MONEY_CHECK((byte)0x04),
    MERCHANT_STATUS_GIVE_MONEY_CHECK_FAILED((byte)0x05);

    public Byte val;
    MerchantStatusEnum(Byte val) {
        this.val = val;
    }
    
    public static MerchantStatusEnum getEnum(Byte val) {
    	MerchantStatusEnum[] values = MerchantStatusEnum.values();
        for (MerchantStatusEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
