package com.lawu.eshop.user.dto;

/**
 *
 * Created by zhangyong on 2017/3/27.
 */
public enum CertifTypeEnum {
    CERTIF_TYPE_IDCARD((byte)0x01),
    CERTIF_TYPE_LICENSE((byte)0x02);
    public Byte val;
    CertifTypeEnum(Byte val) {
        this.val = val;
    }
}
