package com.lawu.eshop.user.dto;

/**
 *
 * Created by Administrator on 2017/3/27.
 */
public enum  MerchantStoreTypeEnum {
    NORMAL_MERCHANT((byte)0x01),
    ENTITY_MERCHANT((byte)0x02);
    private Byte val;
    MerchantStoreTypeEnum(Byte val) {
        this.val = val;
    }

}
