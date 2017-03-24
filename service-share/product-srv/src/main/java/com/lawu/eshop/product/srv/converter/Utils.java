package com.lawu.eshop.product.srv.converter;

/**
 * Created by ${Yangqh} on 2017/3/22.
 */
public class Utils {

    public static byte intToByte(Integer i) {
        int x = i;
        return (byte) x;
    }

    public static Integer byteToInt(byte b) {
        return b & 0xFF;
    }
}
