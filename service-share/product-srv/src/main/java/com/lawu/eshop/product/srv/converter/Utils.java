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
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }
}
